package com.lvbok.fein;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("order")
public interface OrderFeignClient {

    @GetMapping("/index")
    public String index();
}
