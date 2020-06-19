package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

/**
 * @author huyang
 * @date 2020/5/16 13:22
 */
@RestController
@Slf4j
@RequestMapping("/consumer/order")
public class OrderController {

    // 当服务提供方是 集群 的时候就不要用 ip + 端口号来访问了
    // public static final String PAYMENT_URL = "http://localhost:8001";

    /**
     * 集群的时候可以使用微服务的名字来访问，但是需要开启负载均衡的能力！！！
     * 使用@LoadBalanced
     */
    public static final String PAYMENT_URL = "http://CLOUD-PAYMENT-SERVICE";

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/create")
    public CommonResult<Payment> create(@RequestBody Payment payment) {
        log.info("订单系统通过RestTemplate调用支付服务...");
        return restTemplate.postForObject(PAYMENT_URL + "/payment/create", payment, CommonResult.class);
    }

    @GetMapping("/get/{id}")
    public CommonResult<Payment> getPayment(@PathVariable("id") Long id) {
        log.info("订单系统通过RestTemplate调用支付服务...");
        return restTemplate.getForObject(PAYMENT_URL + "/payment/get/" + id, CommonResult.class);
    }

    @GetMapping("/getForEntity/{id}")
    public CommonResult<Payment> gettForEntity(@PathVariable("id") Long id) {
        log.info("订单系统通过RestTemplate调用支付服务...");
        ResponseEntity<CommonResult> entity = restTemplate.getForEntity(PAYMENT_URL + "/payment/get/" + id, CommonResult.class);
        if (entity.getStatusCode().is2xxSuccessful()) {
            return entity.getBody();
        } else {
            return new CommonResult<>(444, "操作失败");
        }
    }
}
