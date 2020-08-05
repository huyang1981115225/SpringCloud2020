package com.atguigu.springcloud;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * SpringCloud 注册中心Eureka
 *
 * @author huyang
 * @date 2020/5/16 15:24
 */
@SpringBootApplication
@EnableEurekaServer
@EnableApolloConfig  // Apollo配置
public class EurekaMain7002 {
    public static void main(String[] args) {
        SpringApplication.run(EurekaMain7002.class, args);
    }
}
