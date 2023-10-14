package com.hmall.user.web;


import com.hmall.common.annotation.UserId;
import com.hmall.pojo.user.entity.Address;
import com.hmall.user.service.IAddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/address")
public class AddressController {

    @Resource
    private IAddressService addressService;


    /**
     * 根据id查询地址详情
     *
     * @param id address id
     * @return address
     */
    @GetMapping("{id}")
    public Address findAddressById(@PathVariable("id") Long id) {
        return addressService.getById(id);
    }

    /**
     * 根据用户id查询用户地址
     *
     * @param userId 用户id
     * @return 用户地址列表
     */
    @GetMapping("/uid/{userId}")
    @UserId
    public List<Address> findAddressByUserId(@PathVariable Long userId) {
        log.info("查询用户地址,用户id:{}", userId);
        return addressService.query().eq("user_id", userId).list();
    }
}
