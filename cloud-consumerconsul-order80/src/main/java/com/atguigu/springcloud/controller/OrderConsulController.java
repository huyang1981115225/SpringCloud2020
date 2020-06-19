package com.atguigu.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

/**
 * @author huyang
 * @date 2020/5/16 13:22
 */
@RestController
@Slf4j
@RequestMapping("/consumer/order")
public class OrderConsulController {

    // 当服务提供方是 集群 的时候就不要用 ip + 端口号来访问了
    // public static final String PAYMENT_URL = "http://localhost:8001";

    /**
     * 集群的时候可以使用微服务的名字来访问，但是需要开启负载均衡的能力！！！
     * 使用@LoadBalanced
     */
    public static final String INVOKE_URL = "http://consul-provider-payment";

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/consul")
    public String paymentInfo() {
        log.info("订单系统访问支付模块 consul ...");
        String result = restTemplate.getForObject(INVOKE_URL + "/payment/consul", String.class);
        return result;
    }

}
