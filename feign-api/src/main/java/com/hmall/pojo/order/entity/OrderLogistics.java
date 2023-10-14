package com.hmall.pojo.order.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_order_logistics")
public class OrderLogistics{
    /**
     * 订单id，与订单表一对一
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long orderId;
    /**
     * 物流单号
     */
    private String logisticsNumber;
    /**
     * 物流名称
     */
    private String logisticsCompany;
    /**
     * 收件人
     */
    private String contact;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 省
     */
    private String province;
    /**
     * 市
     */
    private String city;
    /**
     * 区
     */
    private String town;
    /**
     * 街道
     */
    private String street;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}
