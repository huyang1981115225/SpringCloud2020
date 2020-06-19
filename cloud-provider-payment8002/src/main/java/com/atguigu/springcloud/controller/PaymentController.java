package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

/**
 * @author huyang
 * @date 2020/5/16 12:26
 */
@RestController
// lombok 日志
@Slf4j
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @PostMapping("/create")
    public CommonResult create(@RequestBody Payment payment) {
        int result = paymentService.create(payment);
        log.info(".......插入结果： " + result);
        if (result > 0) {
            return new CommonResult(200, "插入数据库成功,serverPort: " + serverPort, result);
        } else {
            return new CommonResult(444, "插入数据库失败", null);
        }
    }

    @GetMapping("/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id) {
        Payment result = paymentService.getPaymentById(id);
        log.info(".......查询结果： " + result);
        if (result != null) {
            return new CommonResult(200, "查询成功,serverPort: " + serverPort, result);
        } else {
            return new CommonResult(444, "查询失败", null);
        }
    }

    /**
     * 演示网关 9527
     * @return
     */
    @GetMapping("/serverPort/{id}")
    public String getServerPort(@PathVariable("id") Long id) {
        log.info("测试getway...");
        return serverPort;
    }

    @GetMapping("/lb")
    public String getPaymentLb() {
        log.info("测试getway...");
        return serverPort;
    }
}
