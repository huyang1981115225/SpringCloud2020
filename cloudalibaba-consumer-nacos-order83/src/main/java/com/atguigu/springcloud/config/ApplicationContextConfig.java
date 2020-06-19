package com.atguigu.springcloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * 添加 RestTemplate 配置
 *
 * @author huyang
 * @date 2020/5/16 13:26
 */
@Configuration
public class ApplicationContextConfig {

    /**
     * 集群的时候可以使用微服务的名字来访问，但是需要开启负载均衡的能力！！！
     * 使用@LoadBalanced
     * Ribbon 和 Eureka 整合后Consumer可以直接调用服务而不用再关心地址和端口号，且该服务还有负载功能了
     */
    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
