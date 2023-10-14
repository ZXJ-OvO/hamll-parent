package com.hmall.feign;


import com.hmall.pojo.common.dto.PageDTO;
import com.hmall.pojo.common.vo.PageVO;
import com.hmall.pojo.item.entity.Item;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "itemservice")
public interface ItemFeign {


/*    @PutMapping("/item/reduce")
    Boolean decrease(Long itemId, Integer number);*/

    @GetMapping("/item/list")
    PageVO<Item> pageQuery(@SpringQueryMap PageDTO pageDTO);

    @GetMapping("/item/{id}")
    Item getById(@PathVariable Long id);
}
