package com.atguigu.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * 演示 分库分表  ShardingJabc 读写分离
 *
 * @author huyang
 * @date 2020/6/17 15:48
 */
@SpringBootApplication
@EnableEurekaClient
public class ShardingJabcSimpleBootstrap {
    public static void main(String[] args) {
        SpringApplication.run(ShardingJabcSimpleBootstrap.class, args);
    }
}
