package com.hmall.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hmall.order.mapper.OrderMapper;
import com.hmall.order.service.IOrderService;
import com.hmall.pojo.order.entity.Order;
import org.springframework.stereotype.Service;

@Service
public class OrderService extends ServiceImpl<OrderMapper, Order> implements IOrderService {
}
