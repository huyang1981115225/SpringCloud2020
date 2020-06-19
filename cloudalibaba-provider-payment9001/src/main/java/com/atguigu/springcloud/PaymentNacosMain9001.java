package com.atguigu.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author huyang
 * @date 2020/5/17 14:51
 */
@SpringBootApplication
@EnableDiscoveryClient
public class PaymentNacosMain9001 {
    public static void main(String[] args) {
        SpringApplication.run(PaymentNacosMain9001.class, args);
    }
}
