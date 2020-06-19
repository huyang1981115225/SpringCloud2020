package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 在高并发环境下，如果某个方法被多人访问，其他的接口访问速度也会受到影响，
 * 因为：tomcat默认的工作线程数被打满了，没有多余的线程来分解压力和处理！！！
 * <p>
 * jmeter压测结论：
 * 上面还是服务提供者8001自己测试，假如此时外部的消费者80也来访问，
 * 那消费者只能干等，最终导致消费端80不满意，服务端8001直接被拖死
 *
 * @author huyang
 * @date 2020/5/16 20:39
 */
@RestController
@Slf4j
@RequestMapping("/payment/hystrix")
public class PaymentController {

    @Value("${server.port}")
    private String serverPort;

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/ok/{id}")
    public String paymentInfo_ok(@PathVariable("id") Integer id) {
        String result = paymentService.paymentInfo_ok(id);
        log.info("paymentInfo_ok------------Result: " + result);
        return result;
    }

    @GetMapping("/timeout/{id}")
    public String paymentInfo_timeout(@PathVariable("id") Integer id) {
        log.info("-------paymentInfo_timeout-------------");
        String result = paymentService.paymentInfo_timeout(id);
        log.info("paymentInfo_timeout------------Result: " + result);
        return result;
    }

    /**
     * 测试服务熔断
     */
    @GetMapping("/circuit/{id}")
    public String paymentCircuitBreaker(@PathVariable("id") Integer id) {
        String result = paymentService.paymentCircuitBreaker(id);
        log.info("测试服务熔断  Result: " + result);
        return result;
    }
}
