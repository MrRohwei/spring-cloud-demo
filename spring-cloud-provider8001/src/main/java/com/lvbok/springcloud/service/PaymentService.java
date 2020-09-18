package com.lvbok.springcloud.service;

import com.lvbok.springcloud.entities.Payment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

/**
 * @author luwei
 * @date 2020年09月04 16:08
 * @desc
 **/
public interface PaymentService {

    int create(Payment payment);

    Payment getPaymentById(@Param("id") Long id);
}
