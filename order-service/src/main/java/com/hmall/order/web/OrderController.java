package com.hmall.order.web;


import com.hmall.order.service.IOrderService;
import com.hmall.pojo.order.entity.Order;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("order")
public class OrderController {

   @Resource
   private IOrderService orderService;

   @GetMapping("{id}")
   public Order queryOrderById(@PathVariable("id") Long orderId) {
      return orderService.getById(orderId);
   }
}
