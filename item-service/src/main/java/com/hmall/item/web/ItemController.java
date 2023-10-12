package com.hmall.item.web;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hmall.pojo.common.dto.PageDTO;
import com.hmall.pojo.common.vo.PageVO;
import com.hmall.pojo.item.entity.Item;
import com.hmall.item.service.IItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/item")
public class ItemController {

    @Resource
    private IItemService itemService;

    /**
     * 分页查询商品列表
     *
     * @param pageDTO 分页参数
     * @return 商品分页查询结果
     */
    @GetMapping("/list")
    public PageVO<Item> pageQuery(PageDTO pageDTO) {
        Page<Item> page = itemService.page(new Page<>(pageDTO.getPage(), pageDTO.getSize()), null);
        return PageVO.<Item>builder().total(page.getTotal()).list(page.getRecords()).build();
    }

    /**
     * 根据id查询商品
     *
     * @param id 商品id
     * @return 商品对象
     */
    @GetMapping("/{id}")
    public Item getById(@PathVariable Long id) {
        return itemService.getById(id);
    }

    /**
     * 新增商品
     *
     * @param item 商品对象
     * @return 是否新增成功
     */
    @PostMapping()
    public Boolean insert(@RequestBody Item item) {
        return itemService.save(item);
    }


    /**
     * 修改商品上下架状态
     *
     * @param id     商品id
     * @param status 商品上下架状态
     * @return 是否修改成功
     */
    @PutMapping("/status/{id}/{status}")
    public Boolean status(@PathVariable Long id, @PathVariable Integer status) {
        return itemService.updateById(Item.builder().id(id).status(status).build());
    }


    /**
     * 修改商品
     *
     * @param item 商品对象
     * @return 是否修改成功
     */
    @PutMapping()
    public Boolean update(@RequestBody Item item) {
        return itemService.updateById(item);
    }


    /**
     * 删除商品
     *
     * @param id 商品id
     * @return 是否删除成功
     */
    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Long id) {
        return itemService.removeById(id);
    }
}
