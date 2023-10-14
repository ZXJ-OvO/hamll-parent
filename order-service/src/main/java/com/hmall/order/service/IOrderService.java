package com.hmall.order.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.hmall.pojo.order.entity.Order;
import com.hmall.pojo.search.doc.RequestParams;

public interface IOrderService extends IService<Order> {
    Long createOrder(RequestParams requestParams);

}
