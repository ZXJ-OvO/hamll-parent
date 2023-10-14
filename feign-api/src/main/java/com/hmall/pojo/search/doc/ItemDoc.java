package com.hmall.pojo.search.doc;


import com.hmall.pojo.item.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemDoc {

    private Long id;//商品id
    private String name;//商品名称
    private Long price;//价格（分）
    //  private Integer stock;//库存数量
    private String image;//商品图片
    private String category;//分类名称
    private String brand;//品牌名称
    //   private String spec;//规格
    private Integer sold;//销量
    private Integer commentCount;//评论数
    private Boolean isAD;//商品状态 1-正常，2-下架

    // 自动补全字段
    private List<String> suggestion = new ArrayList<>(2);

    public ItemDoc(Item item) {
        BeanUtils.copyProperties(item, this);

        //this.suggestion = Arrays.asList(this.brand, this.category);
        // 补全
        suggestion.add(item.getBrand());
        suggestion.add(item.getCategory());
    }
}
