package com.hmall.item.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hmall.pojo.item.entity.Item;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ItemMapper extends BaseMapper<Item> {
}
