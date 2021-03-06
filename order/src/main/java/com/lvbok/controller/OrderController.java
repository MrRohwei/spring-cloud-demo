package com.lvbok.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @Value("${server.port}")
    private int port;

    @GetMapping("/order/index")
    public String index() {
        return "order的端口：" + port;
    }
}
