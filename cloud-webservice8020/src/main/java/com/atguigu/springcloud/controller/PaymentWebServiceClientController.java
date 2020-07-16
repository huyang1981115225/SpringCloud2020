package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentWebService;
import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 模拟WebService客户端进行测试
 *
 * @author huyang
 * @date 2020/7/16 11:38
 */
@RestController
@RequestMapping("/webservice")
@Slf4j
public class PaymentWebServiceClientController {

    @GetMapping("/test")
    public void testWebService() {
        cl1();

        cl2();

    }

    /**
     * 方式1.代理类工厂的方式,需要拿到对方的接口
     */
    public static void cl1() {
        try {
            System.out.println("------------------代理类工厂的方式------------------");
            // 接口地址
            String address = "http://localhost:8020/services/PaymentWebService?wsdl";
            // 代理工厂
            JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
            // 设置代理地址
            jaxWsProxyFactoryBean.setAddress(address);
            // 设置接口类型
            jaxWsProxyFactoryBean.setServiceClass(PaymentWebService.class);
            // 创建一个代理接口实现
            PaymentWebService pws = (PaymentWebService) jaxWsProxyFactoryBean.create();


            // 数据准备
            Payment payment = new Payment();
            payment.setSerial("Hello,服务端... 代理类工厂");

            CommonResult result = pws.createPayment(payment);
            System.out.println("调用WebService，服务端返回的信息： " + result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 动态调用方式
     */
    public static void cl2() {
        System.out.println("------------------动态调用方式------------------");
        // 创建动态客户端
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient("http://localhost:8020/services/PaymentWebService?wsdl");
        // 需要密码的情况需要加上用户名和密码
        // client.getOutInterceptors().add(new ClientLoginInterceptor(USER_NAME,
        // PASS_WORD));
        Object[] objects = new Object[0];
        try {
            // invoke("方法名",参数1,参数2,参数3....);
            Payment payment = new Payment();
            payment.setSerial("Hello,服务端...动态调用");
//            objects = client.invoke("createPayment", payment);
            System.out.println("返回数据:" + objects[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
