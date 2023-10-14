package com.hmall.pojo.search.doc;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestParams {
    private String key;
    private Integer page = 1;
    private Integer size = 5;
    private String sortBy;
    private String brand;
    private String category;
    private Long minPrice;
    private Long maxPrice;
}
