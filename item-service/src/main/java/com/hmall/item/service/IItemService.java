package com.hmall.item.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hmall.pojo.item.entity.Item;

public interface IItemService extends IService<Item> {
    Boolean ReduceInventory(Long itemId, Integer number);
}
