package com.atguigu.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author huyang
 * @date 2020/5/17 15:18
 */
@RestController
@Slf4j
public class OrderNacosController {

    @Value("${service-url.nacos-user-service}")
    private String serverUrl;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/consumer/payment/nacos/{id}")
    public String getPayment(@PathVariable("id") Integer id) {
        log.info("Nacos  订单系统访问支付模块......");
        return restTemplate.getForObject(serverUrl + "/payment/nacos/" + id, String.class);

    }
}
