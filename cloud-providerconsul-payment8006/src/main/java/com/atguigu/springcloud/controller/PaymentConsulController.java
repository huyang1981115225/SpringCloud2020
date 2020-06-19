package com.atguigu.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author huyang
 * @date 2020/5/16 17:09
 */
@RestController
@Slf4j
@RequestMapping("/payment")
public class PaymentConsulController {

    @Value("${server.port}")
    private String serverPort;

    @RequestMapping("/consul")
    public String paymentConsul() {
        return "springcloud with consul: " + serverPort + "\t" + UUID.randomUUID().toString();
    }
}
