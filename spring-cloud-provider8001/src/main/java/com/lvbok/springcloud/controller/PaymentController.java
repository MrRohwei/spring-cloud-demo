package com.lvbok.springcloud.controller;

import com.lvbok.springcloud.entities.CommonResult;
import com.lvbok.springcloud.entities.Payment;
import com.lvbok.springcloud.service.PaymentService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author luwei
 * @date 2020年09月04 16:13
 * @desc
 **/
@RestController
@Log4j2
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @RequestMapping("/")
    public String hello() {
        return "hello";
    }

    @PostMapping(value = "/payment/create")
    public CommonResult create(Payment payment) {
        int result = paymentService.create(payment);
        log.info("*****插入结果" + result);
        if (result > 0) {
            return new CommonResult(200, "插入数据库成功", result);
        } else {
            return new CommonResult(444, "插入数据库失败");
        }
    }

    @GetMapping(value = "/payment/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id) {
        Payment payment = paymentService.getPaymentById(id);
        log.info("*****查询结果" + payment);
        if (payment != null) {
            return new CommonResult(200, "查询成功", payment);
        } else {
            return new CommonResult(444, String.format("没有查询记录[s%]", id));
        }
    }
}
