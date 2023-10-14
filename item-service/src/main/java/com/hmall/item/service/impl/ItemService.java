package com.hmall.item.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hmall.item.mapper.ItemMapper;
import com.hmall.item.service.IItemService;
import com.hmall.pojo.item.entity.Item;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ItemService extends ServiceImpl<ItemMapper, Item> implements IItemService {

    @Resource
    private ItemMapper itemMapper;


    @Override
    public Boolean ReduceInventory(Long itemId, Integer number) {
        Item item = itemMapper.selectById(itemId);
        Integer stock = item.getStock();

        if (stock - number < 0) {
            throw new RuntimeException("库存不足");
        }

        item.setStock(stock - number);
        itemMapper.updateById(item);

        return true;
    }
}
