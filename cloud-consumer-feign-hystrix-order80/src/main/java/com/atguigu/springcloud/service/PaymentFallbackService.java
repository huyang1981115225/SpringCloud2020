package com.atguigu.springcloud.service;

import org.springframework.stereotype.Component;

/**
 * @author huyang
 * @date 2020/5/17 10:15
 */

@Component
public class PaymentFallbackService implements PaymentFeignService {
    @Override
    public String paymentInfo_ok(Integer id) {
        return "--------------PaymentFallbackService Fall back paymentInfo_ok";
    }

    @Override
    public String paymentInfo_timeout(Integer id) {
        return "--------------PaymentFallbackService Fall back paymentInfo_timeout";
    }
}
