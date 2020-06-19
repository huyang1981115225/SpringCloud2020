package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentFeignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author huyang
 * @date 2020/5/16 19:30
 */
@RestController
@Slf4j
@RequestMapping("/consumer/order")
public class OrderFeignController {

    @Autowired
    private PaymentFeignService paymentFeignService;

    @GetMapping("/feign/get/{id}")
    public CommonResult<Payment> getPayment(@PathVariable("id") Long id) {
        log.info("订单系统通过Feign调用支付服务...");
        return paymentFeignService.getPaymentById(id);
    }

    /**
     * open-feign-ribbon 客户端 默认等待1秒钟
     * @return
     */
    @GetMapping("/feign/timeout")
    public String paymentFeignTimeOut(){
        return paymentFeignService.paymentFeignTimeOut();
    }
}
