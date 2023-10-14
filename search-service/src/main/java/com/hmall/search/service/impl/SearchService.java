package com.hmall.search.service.impl;

import cn.hutool.json.JSONUtil;
import com.hmall.feign.ItemFeign;
import com.hmall.pojo.common.vo.PageVO;
import com.hmall.pojo.item.entity.Item;
import com.hmall.pojo.search.doc.ItemDoc;
import com.hmall.pojo.search.doc.RequestParams;
import com.hmall.search.service.ISearchService;
import lombok.SneakyThrows;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.completion.CompletionSuggestion;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SearchService implements ISearchService {

    @Resource
    private RestHighLevelClient restHighLevelClient;

    @Resource
    private ItemFeign itemFeign;


    private void basicBuild(SearchRequest request, RequestParams params) {
        // 创建布尔查询
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

        // key
        String key = params.getKey();
        if (StringUtils.isNotBlank(key)) {
            boolQuery.must(QueryBuilders.matchQuery("all", key));
        } else {
            boolQuery.must(QueryBuilders.matchAllQuery());
        }

        //  brand
        String brand = params.getBrand();
        if (StringUtils.isNotBlank(brand)) {
            boolQuery.filter(QueryBuilders.termQuery("brand", brand));
        }
        //  category
        String category = params.getCategory();
        if (StringUtils.isNotBlank(category)) {
            boolQuery.filter(QueryBuilders.termQuery("category", category));
        }
        //  price
        Long minPrice = params.getMinPrice();
        Long maxPrice = params.getMaxPrice();
        if (minPrice != null && maxPrice != null) {
            boolQuery.filter(QueryBuilders.rangeQuery("price").gte(minPrice * 100).lte(maxPrice * 100));
        }


        FunctionScoreQueryBuilder queryBuilder = QueryBuilders.functionScoreQuery(boolQuery,
                new FunctionScoreQueryBuilder.FilterFunctionBuilder[]{
                        new FunctionScoreQueryBuilder.FilterFunctionBuilder(
                                QueryBuilders.termQuery("isAD", true),
                                ScoreFunctionBuilders.weightFactorFunction(100)
                        )
                }
        );

        request.source().query(queryBuilder);
    }


    @Override
    @SneakyThrows
    public List<String> getSuggestion(String prefix) {

        SearchRequest request = new SearchRequest("item");
        request.source().fetchSource(new String[0], null);
        request.source().suggest(new SuggestBuilder()
                .addSuggestion("my_suggestion",
                        SuggestBuilders.completionSuggestion("suggestion")
                                .size(10)
                                .skipDuplicates(true)
                                .prefix(prefix)));

        SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        CompletionSuggestion suggestion = response.getSuggest().getSuggestion("my_suggestion");

        List<CompletionSuggestion.Entry.Option> options = suggestion.getOptions();
        List<String> list = new ArrayList<>(options.size());

        for (CompletionSuggestion.Entry.Option option : options) {
            list.add(option.getText().string());
        }
        return list;
    }


    @Override
    @SneakyThrows
    public Map<String, List<String>> getFilters(RequestParams params) {

        SearchRequest searchRequest = new SearchRequest("item");

        // DSL
        searchRequest.source().size(0);
        basicBuild(searchRequest, params);

        // 聚合条件
        searchRequest.source().aggregation(
                AggregationBuilders.terms("brandAgg").field("brand").size(200)
        );
        searchRequest.source().aggregation(
                AggregationBuilders.terms("categoryAgg").field("category").size(20)
        );

        // 发出请求聚合结果
        SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        Aggregations aggregations = response.getAggregations();

        Map<String, List<String>> map = new HashMap<>(2);
        // 根据名称获取品牌聚合

        Terms brandAgg = aggregations.get("brandAgg");
        List<String> brandList = new ArrayList<>();
        for (Terms.Bucket bucket : brandAgg.getBuckets()) {
            String key = bucket.getKeyAsString();
            brandList.add(key);
        }
        map.put("brand", brandList);

        Terms categoryAgg = aggregations.get("categoryAgg");
        List<String> categoryList = new ArrayList<>();
        for (Terms.Bucket bucket : categoryAgg.getBuckets()) {
            String key = bucket.getKeyAsString();
            categoryList.add(key);
        }
        map.put("category", categoryList);

        return map;
    }


    @Override
    @SneakyThrows
    public PageVO<ItemDoc> search(RequestParams params) {

        SearchRequest request = new SearchRequest("item");
        basicBuild(request, params);

        // 分页
        int page = params.getPage();
        int size = params.getSize();
        request.source().from((page - 1) * size).size(size);

        // 排序
        String sortBy = params.getSortBy();
        if ("sold".equals(sortBy)) {
            // 销量降序
            request.source().sort(sortBy, SortOrder.DESC);
        } else if ("price".equals(sortBy)) {
            // 价格升序
            request.source().sort(sortBy, SortOrder.ASC);
        }


        // 高亮
        request.source().highlighter(new HighlightBuilder().field("name").requireFieldMatch(false));

        // 发请求解析结果
        SearchResponse searchResponse = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        SearchHits searchHits = searchResponse.getHits();

        long total = searchHits.getTotalHits().value;
        SearchHit[] hits = searchHits.getHits();

        List<ItemDoc> list = new ArrayList<>(hits.length);
        for (SearchHit hit : hits) {
            String json = hit.getSourceAsString();
            ItemDoc itemDoc = JSONUtil.toBean(json, ItemDoc.class);

            // 获取高亮
            Map<String, HighlightField> map = hit.getHighlightFields();
            if (map != null && !map.isEmpty()) {
                HighlightField field = map.get("name");
                String name = field.getFragments()[0].string();
                itemDoc.setName(name);
            }
            list.add(itemDoc);
        }
        return new PageVO<>(total, list);
    }


    @Override
    @SneakyThrows
    public void saveItemById(Long id) {
        Item item = itemFeign.getById(id);
        ItemDoc itemDoc = new ItemDoc(item);
        IndexRequest request = new IndexRequest("item").id(id.toString());
        request.source(JSONUtil.toJsonStr(itemDoc), XContentType.JSON);
        restHighLevelClient.index(request, RequestOptions.DEFAULT);
    }


    @Override
    @SneakyThrows
    public void deleteItemById(Long id) {
        DeleteRequest deleteRequest = new DeleteRequest("item", id.toString());
        restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
    }
}
