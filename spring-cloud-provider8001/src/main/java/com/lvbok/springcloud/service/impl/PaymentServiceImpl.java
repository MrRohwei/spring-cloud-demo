package com.lvbok.springcloud.service.impl;

import com.lvbok.springcloud.dao.PaymentDao;
import com.lvbok.springcloud.entities.Payment;
import com.lvbok.springcloud.service.PaymentService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author luwei
 * @date 2020年09月04 16:09
 * @desc
 **/
@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentDao paymentDao;

    public int create(Payment payment) {
        return paymentDao.create(payment);
    }

    public Payment getPaymentById(Long id) {
        return paymentDao.getPaymentById(id);
    }
}
