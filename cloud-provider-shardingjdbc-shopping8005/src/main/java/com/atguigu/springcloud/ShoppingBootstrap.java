package com.atguigu.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author huyang
 * @date 2020/6/18 15:28
 */
@SpringBootApplication
@EnableEurekaClient
public class ShoppingBootstrap {
    public static void main(String[] args) {
        SpringApplication.run(ShoppingBootstrap.class, args);
    }
}
