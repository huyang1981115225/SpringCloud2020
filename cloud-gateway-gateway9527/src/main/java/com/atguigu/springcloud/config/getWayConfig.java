package com.atguigu.springcloud.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 网关的两种配置方式：
 * 1、yml配置
 * 2、Config配置
 *
 * 通过9527网关访问到外网的百度新闻网址
 *
 * @author huyang
 * @date 2020/5/17 12:08
 */

@Configuration
public class getWayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder routeLocatorBuilder) {
        RouteLocatorBuilder.Builder routers = routeLocatorBuilder.routes();
        routers.route("path_rote_atguigu",
                r -> r.path("/guonei")
                        .uri("http://news.baidu.com/guonei")).build();
        return routers.build();
    }

    @Bean
    public RouteLocator customRouteLocator2(RouteLocatorBuilder routeLocatorBuilder) {
        RouteLocatorBuilder.Builder routers = routeLocatorBuilder.routes();
        routers.route("path_rote_atguigu",
                r -> r.path("/guoji")
                        .uri("http://news.baidu.com/guoji")).build();
        return routers.build();
    }
}
