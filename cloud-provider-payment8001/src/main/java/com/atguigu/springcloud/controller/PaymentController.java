package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author huyang
 * @Api()：对类进行解释说明 ags：说明该类的作用，参数是个数组，可以填多个。
 * value="该参数没什么意义，在UI界面上不显示，所以不用配置"
 * description = "用户基本信息操作"
 * @ApiOperation()：对方法进行解释说明 value="方法的用途和作用"
 * notes="方法的注意事项和备注"
 * @ApiModel()：解释说明实体作用 description="描述实体的作用"
 * @ApiModelProperty： 描述实体类的属性  value="用户名"  描述参数的意义
 * name="name"    参数的变量名
 * required=true     参数是否必选
 * <p>
 * ...还有很多自行百度
 * @date 2020/5/16 12:26
 */
@Api(value = "支付管理")
@RestController
// lombok 日志
@Slf4j
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;


    /**
     * 对于注册进Eureka里的微服务，可以通过服务发现来获得该服务的信息
     */
    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("/discovery")
    public Object discovery() {
        List<String> services = discoveryClient.getServices();
        for (String element : services) {
            log.info("......Element: " + element);
        }

        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for (ServiceInstance instance : instances) {
            log.info(instance.getServiceId() + "\t" + instance.getHost() + "\t" + instance.getPort() + "\t" + instance.getUri());
        }
        return this.discoveryClient;
    }

    @PostMapping("/create")
    public CommonResult create(@RequestBody Payment payment) {
        int result = paymentService.create(payment);
        log.info(".......插入结果： " + result);
        if (result > 0) {
            return new CommonResult(200, "插入数据库成功,serverPort: " + serverPort, result);
        } else {
            return new CommonResult(444, "插入数据库失败", null);
        }
    }

    @GetMapping("/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id) {
        Payment result = paymentService.getPaymentById(id);
        log.info(".......查询结果： " + result);
        if (result != null) {
            return new CommonResult(200, "查询成功,serverPort: " + serverPort, result);
        } else {
            return new CommonResult(444, "查询失败", null);
        }
    }

    @GetMapping("/feign/timeout")
    public String paymentFeignTimeOut() {
        log.info(serverPort + "演示Feign超时..." + new Date());
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return serverPort;
    }

    /**
     * 演示网关 9527
     *
     * @return
     */
    @GetMapping("/serverPort/{id}")
    public String getServerPort(@PathVariable("id") Long id) {
        log.info("测试getway...");
        return serverPort;
    }

    @GetMapping("/lb")
    public String getPaymentLb() {
        log.info("测试getway...");
        return serverPort;
    }

    /**
     * 基于缓存实现查询.
     *
     * @param limitId
     * @return
     */
    @ApiOperation(value = "获取支付情况")
    @GetMapping(value = "/querycache")
    public Payment queryWithCache(@RequestParam(value = "id") Long id) {
        log.info("带缓存的查询...");
        return paymentService.getPaymentById(id);
    }

    /**
     * 修改同时更新缓存.
     *
     * @param payment
     * @return
     */
    @PostMapping(value = "/updatecache")
    public Payment updateWithCache(@RequestBody Payment payment) {
        return paymentService.update(payment);
    }

    /**
     * 删除同时清理缓存.
     *
     * @param id
     * @return
     */
    @PostMapping(value = "/deletecache")
    public void deleteWithCache(@RequestParam(value = "id") Long id) {
        log.info("删除的同时删除缓存...");
        paymentService.delete(id);
    }
}
