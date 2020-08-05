package com.atguigu.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * SpringCloud alibaba nacos 服务提供方
 *
 * @author huyang
 * @date 2020/5/17 15:02
 */
@SpringBootApplication
@EnableDiscoveryClient
public class PaymentNacosMain9002 {

    public static void main(String[] args) {
        SpringApplication.run(PaymentNacosMain9002.class, args);
    }
}
