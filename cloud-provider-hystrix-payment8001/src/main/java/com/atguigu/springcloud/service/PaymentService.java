package com.atguigu.springcloud.service;

import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author huyang
 * @date 2020/5/16 20:36
 */
public interface PaymentService {
    String paymentInfo_ok(Integer id);

     String paymentInfo_timeout(Integer id);

    String paymentCircuitBreaker(Integer id);
}
