package com.atguigu.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author huyang
 * @date 2020/7/16 12:32
 */
@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient  // 对于注册进Eureka里的微服务，可以通过服务发现来获得该服务的信息
public class WebServiceMain8020 {
    public static void main(String[] args) {
        SpringApplication.run(WebServiceMain8020.class, args);
    }
}
