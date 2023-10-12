package com.hmall.item.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hmall.item.mapper.ItemMapper;
import com.hmall.item.service.IItemService;
import com.hmall.pojo.item.entity.Item;
import org.springframework.stereotype.Service;

@Service
public class ItemService extends ServiceImpl<ItemMapper, Item> implements IItemService {
}
