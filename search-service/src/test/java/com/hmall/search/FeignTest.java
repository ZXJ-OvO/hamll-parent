package com.hmall.search;


import cn.hutool.json.JSONUtil;
import com.hmall.feign.ItemFeign;
import com.hmall.pojo.common.dto.PageDTO;
import com.hmall.pojo.common.vo.PageVO;
import com.hmall.pojo.item.entity.Item;
import com.hmall.pojo.search.doc.ItemDoc;
import lombok.SneakyThrows;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FeignTest {
    @Resource
    private ItemFeign itemFeign;

    @Resource
    private RestHighLevelClient restHighLevelClient;

    @Test
    @SneakyThrows
    public void testQueryItem() {

        int page = 1;
        PageDTO pageDTO = new PageDTO();
        pageDTO.setSize(500);
        while (true) {
            pageDTO.setPage(page);
            PageVO<Item> pageVO = itemFeign.pageQuery(pageDTO);
            List<Item> list = pageVO.getList();

            if (list.isEmpty()) {
                break;
            }

            BulkRequest bulkRequest = new BulkRequest();

            for (Item item : list) {
                // 遇到下架商品跳过不导入
                if (item.getStatus() == 2){
                    continue;
                }

                // 把Item转化成ItemDoc
                ItemDoc itemDoc = new ItemDoc(item);
                bulkRequest.add(new IndexRequest("item")
                        .id(itemDoc.getId().toString())
                        .source(JSONUtil.toJsonStr(itemDoc), XContentType.JSON)
                );
            }

            BulkResponse response = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);

            page++;
        }
    }
}
