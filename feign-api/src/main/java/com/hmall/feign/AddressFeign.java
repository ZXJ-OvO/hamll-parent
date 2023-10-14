package com.hmall.feign;


import com.hmall.pojo.user.entity.Address;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "user-service")
public interface AddressFeign {

    @GetMapping("/address/{id}")
    Address findAddressById(@PathVariable("id") Long id);

}
