package com.atguigu.springcloud.service.impl;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentWebService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.jws.WebService;

/**
 * @author huyang
 * @date 2020/7/16 11:29
 */
@WebService(serviceName = "PaymentWebService",
        targetNamespace = "http://localhost:8020",
        endpointInterface = "com.atguigu.springcloud.service.PaymentWebService")
@Component
@Slf4j
public class PaymentWebServiceImpl implements PaymentWebService {
    @Override
    public CommonResult createPayment(Payment payment) {
        log.info("WebService客户端发来的信息：  " + payment );
        CommonResult commonResult = new CommonResult();
        commonResult.setCode(200);
        commonResult.setMessage("Hello,客户端，调用WebService成功。");
        return commonResult;
    }
}
