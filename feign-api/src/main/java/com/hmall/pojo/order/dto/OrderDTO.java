package com.hmall.pojo.order.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    /**
     * 购买数量
     */
    private Integer num;
    /**
     * 支付方式
     */
    private Integer paymentType;
    /**
     * 买家地址id
     */
    private Long addressId;
    /**
     * 商品id
     */
    private Long itemId;
}
