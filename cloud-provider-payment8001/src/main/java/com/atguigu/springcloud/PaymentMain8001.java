package com.atguigu.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * SpringCloud 服务提供方 加入了Redis缓存，整合了Swagger  整合了日志，整合了Apollo配置中心
 *
 * @author huyang
 * @date 2020/5/15 23:49
 */
@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient  // 对于注册进Eureka里的微服务，可以通过服务发现来获得该服务的信息
@EnableCaching
@EnableSwagger2
public class PaymentMain8001 {
    public static void main(String[] args) {
        SpringApplication.run(PaymentMain8001.class, args);
    }
}
