package com.hmall.pojo.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_address")
public class Address {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private String contact;// 收件人姓名
    private String mobile;// 电话
    private String province;// 省份
    private String city;// 城市
    private String town;// 区
    private String street;// 街道地址
    private Boolean isDefault;
}
