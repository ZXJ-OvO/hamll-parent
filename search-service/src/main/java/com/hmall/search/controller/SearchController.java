package com.hmall.search.controller;


import com.hmall.pojo.common.vo.PageVO;
import com.hmall.pojo.search.doc.ItemDoc;
import com.hmall.pojo.search.doc.RequestParams;
import com.hmall.search.service.ISearchService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/search")
public class SearchController {

    @Resource
    private ISearchService searchService;


    @GetMapping("/suggestion")
    public List<String> getSuggestion(@RequestParam("key") String prefix) {
        return searchService.getSuggestion(prefix);
    }


    @PostMapping("/filters")
    public Map<String, List<String>> getFilters(@RequestBody RequestParams params) {
        return searchService.getFilters(params);
    }


    @PostMapping("/list")
    public PageVO<ItemDoc> search(@RequestBody RequestParams params) {
        return searchService.search(params);
    }
}
