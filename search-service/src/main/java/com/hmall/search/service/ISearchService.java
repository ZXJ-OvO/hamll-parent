package com.hmall.search.service;


import com.hmall.pojo.common.vo.PageVO;
import com.hmall.pojo.search.doc.ItemDoc;
import com.hmall.pojo.search.doc.RequestParams;

import java.util.List;
import java.util.Map;

public interface ISearchService {
    List<String> getSuggestion(String prefix);

    Map<String, List<String>> getFilters(RequestParams params);

    PageVO<ItemDoc> search(RequestParams params);

    void saveItemById(Long id);

    void deleteItemById(Long id);
}
