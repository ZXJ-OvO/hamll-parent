package com.hmall.order.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hmall.order.mapper.OrderMapper;
import com.hmall.order.service.IOrderService;
import com.hmall.pojo.order.entity.Order;
import com.hmall.pojo.search.doc.RequestParams;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class OrderService extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    /*    @Resource
        private ItemClient itemClient;
        @Resource
        private UserClient userClient;*/
    @Resource
    private OrderDetailService detailService;
    @Resource
    private OrderLogisticsService logisticsService;

    @Transactional
    @Override
    public Long createOrder(RequestParams requestParams) {
/*        Order order = new Order();
        // 1.查询商品
        Item item = itemClient.queryItemById(requestParams.getItemId());
        // 2.基于商品价格、购买数量计算商品总价：totalFee
        long totalFee = item.getPrice() * requestParams.getNum();
        order.setTotalFee(totalFee);
        order.setPaymentType(requestParams.getPaymentType());
        order.setUserId(UserHolder.getUser());
        order.setStatus(1);
        // 3.将Order写入数据库tb_order表中
        this.save(order);

        // 4.将商品信息、orderId信息封装为OrderDetail对象，写入tb_order_detail表
        OrderDetail detail = new OrderDetail();
        detail.setName(item.getName());
        detail.setSpec(item.getSpec());
        detail.setPrice(item.getPrice());
        detail.setNum(requestParams.getNum());
        detail.setItemId(item.getId());
        detail.setImage(item.getImage());
        detail.setOrderId(order.getId());
        detailService.save(detail);


        Long addressId = requestParams.getAddressId();
        // 5.根据addressId查询user-service服务，获取地址信息
        Address address = userClient.findAddressById(addressId);
        // 6.将地址封装为OrderLogistics对象，写入tb_order_logistics表
        OrderLogistics orderLogistics = new OrderLogistics();
        BeanUtils.copyProperties(address, orderLogistics);
        orderLogistics.setOrderId(order.getId());
        logisticsService.save(orderLogistics);

        // 7.扣减库存
        try {
            itemClient.updateStock(requestParams.getItemId(), requestParams.getNum());
        } catch (Exception e) {
            throw new RuntimeException("库存不足！");
        }
        return order.getId();*/
        return 1l;
    }
}
