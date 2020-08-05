package com.atguigu.springcloud;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author huyang
 * @date 2020/5/16 14:36
 */
@SpringBootApplication
@EnableEurekaServer
@EnableApolloConfig  // Apollo配置
public class EurekaMain7001 {
    public static void main(String[] args) {
        SpringApplication.run(EurekaMain7001.class, args);
    }
}
