package com.atguigu.springcloud.config;

import com.atguigu.springcloud.service.PaymentWebService;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

/**
 * WebService公共配置类
 * @author huyang
 * @date 2020/7/16 11:20
 */
@Configuration
public class WebServiceConfig {
    @Autowired
    private Bus bus;

    @Autowired
    private PaymentWebService paymentWebService;

    @Bean
    public Endpoint endpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, paymentWebService);
//        endpoint.getInInterceptors().add(new AuthInterceptor());//如果需要则添加账号密码校验拦截器
        endpoint.publish("/PaymentWebService");
        return endpoint;
    }


    /**
     * 如果配置多个可以继续配置
     */
//    @Bean
//    public Endpoint endpoint2() {
//        EndpointImpl endpoint = new EndpointImpl(bus, xqsService);
////        endpoint.getInInterceptors().add(new AuthInterceptor());//如果需要则添加账号密码校验拦截器
//        endpoint.publish("/XqsService");
//        return endpoint;
//    }
//
//    @Bean
//    public Endpoint endpoint3() {
//        EndpointImpl endpoint = new EndpointImpl(bus, yuspService);
////        endpoint.getInInterceptors().add(new AuthInterceptor());//如果需要则添加账号密码校验拦截器
//        endpoint.publish("/YuspService");
//        return endpoint;
//    }

}
