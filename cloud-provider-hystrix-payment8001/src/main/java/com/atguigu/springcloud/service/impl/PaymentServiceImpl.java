package com.atguigu.springcloud.service.impl;

import cn.hutool.core.util.IdUtil;
import com.atguigu.springcloud.service.PaymentService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.TimeUnit;

/**
 * @author huyang
 * @date 2020/5/16 20:36
 */
@Service
public class PaymentServiceImpl implements PaymentService {
    @Override
    public String paymentInfo_ok(Integer id) {
        return "线程池： " + Thread.currentThread().getName() + "\t paymentInfo_ok,id: " + id + "\t O(∩_∩)O哈哈~。";
    }

    /**
     * 下面是服务降级 fallback
     */
    @Override
    // 如果出事了需要一个方法来解决：服务降级----fallback
    // 无论下面一个方法是出异常还是超过设定的时间峰值，都会进入服务降级所指定的方法：paymentInfo_timeoutHandler
    @HystrixCommand(fallbackMethod = "paymentInfo_timeoutHandler", commandProperties = {
            // 假设3秒钟是正常业务逻辑，如果超过3秒就会降级处理
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")})
    public String paymentInfo_timeout(Integer id) {
        int timeNumber = id;
        // 出异常还是超过设定的时间峰值，都会进入服务降级所指定的方法
//        int age = 10/0;
        try {
            TimeUnit.SECONDS.sleep(timeNumber);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池： " + Thread.currentThread().getName() + "\t 不错哦，处理速度不错，未超过峰值3秒钟,id: " + id + "\t" + " O(∩_∩)O哈哈~,"
                + "耗时" + timeNumber + "秒钟。";
    }

    public String paymentInfo_timeoutHandler(Integer id) {
        return "线程池： " + Thread.currentThread().getName() + "\t 提供者8001系统繁忙，请稍后再试,id: " + id + "\t" + "(*^▽^*),开始服务降级...";
    }

    /**
     * 下面是服务熔断
     */
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),// 是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),// 请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),// 时间范围
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60") // 失败率达到多少后跳闸
    })
    @Override
    public String paymentCircuitBreaker(@PathVariable("id") Integer id) {
        if (id < 0) {
            throw new RuntimeException("*****id 不能负数");
        }
        String serialNumber = IdUtil.simpleUUID();

        return Thread.currentThread().getName() + "\t" + "调用成功,流水号：" + serialNumber;
    }

    public String paymentCircuitBreaker_fallback(@PathVariable("id") Integer id) {
        return "进入熔断了,id 不能负数，请稍候再试,(┬＿┬)/~~     id: " + id;
    }
}
