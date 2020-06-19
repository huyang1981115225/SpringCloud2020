package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.service.PaymentFeignService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 1、如果每个方法都配置一个兜底的方法，就很膨胀
 * 2、方法和业务逻辑混在一起，非常的混乱
 *
 * @author huyang
 * @date 2020/5/16 21:12
 */
@RestController
@Slf4j
@RequestMapping("/consumer/hystrix/order")
// 全局的FallBack
@DefaultProperties(defaultFallback = "payment_Global_FallBackMethod")
public class OrderHystrixController {

    @Autowired
    private PaymentFeignService paymentFeignService;

    /**
     * 测试全局FallBack
     */
    @GetMapping("/ok/{id}")
    //    @HystrixCommand // TODO 别忘了加这个标签Hystrix
    // 也可以交给FeignFallBack管理 -----PaymentFallbackService
    public String paymentInfo_ok(@PathVariable("id") Integer id) {
        String result = paymentFeignService.paymentInfo_ok(id);
        log.info("paymentInfo_ok------------Result: " + result);
        return result;
    }

    /**
     * open-feign-ribbon 客户端 默认等待1秒钟
     * @return
     */
    // 如果出事了需要一个方法来解决：服务降级----fallback
    // 无论下面一个方法是出异常还是超过设定的时间峰值，都会进入服务降级所指定的方法：paymentInfo_timeoutHandler
    @HystrixCommand(fallbackMethod = "paymentInfo_timeoutHandler",commandProperties = {
            // 假设3秒钟是正常业务逻辑，如果超过3秒就会降级处理
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "1500")})
    @GetMapping("/timeout/{id}")
    public String paymentInfo_timeout(@PathVariable("id") Integer id){
        log.info("Consumer留给服务端的时间是1.5s");
        String result = paymentFeignService.paymentInfo_timeout(id);
        log.info("paymentInfo_timeout------------Result: " + result);
        return result;
    }

    public String paymentInfo_timeoutHandler(Integer id) {
        return "Hello,我是消费者80，对方支付系统繁忙，请10秒钟后再试，或者自己运行有错检查自己o(*￣︶￣*)o";
    }


    // 每个方法配置一个FallBack不现实，所以下面配置一个全局的
    public String payment_Global_FallBackMethod(){
        return "Global异常处理信息，请稍后再试...";
    }
}
