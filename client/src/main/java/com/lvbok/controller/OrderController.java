package com.lvbok.controller;

import com.lvbok.fein.OrderFeignClient;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderFeignClient orderFeignClient;

    @GetMapping("/index")
    public String index() {
        log.info("order index=========");
        return orderFeignClient.index();
    }
}
