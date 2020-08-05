package com.atguigu.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * SpringCloud 网关 gateway
 * @author huyang
 * @date 2020/5/17 11:36
 */
@SpringBootApplication
@EnableEurekaClient
public class GetWayMain9527 {
    public static void main(String[] args) {
        SpringApplication.run(GetWayMain9527.class, args);
    }
}
