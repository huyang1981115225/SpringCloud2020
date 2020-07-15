package com.atguigu.springcloud.service;

import com.atguigu.springcloud.entities.Payment;
import org.apache.ibatis.annotations.Param;

/**
 * @author huyang
 * @date 2020/5/16 12:23
 */
public interface PaymentService {

    int create(Payment payment);

    Payment getPaymentById(@Param("id") Long id);

    Payment update(Payment payment);

    int delete(@Param("id") Long id);
}
