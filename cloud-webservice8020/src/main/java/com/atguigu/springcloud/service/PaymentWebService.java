package com.atguigu.springcloud.service;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 * @author huyang
 * @date 2020/7/16 11:26
 */
@WebService(name = "PaymentWebService", // 暴露服务名称
        targetNamespace = "http://localhost:8020")   //命名空间
public interface PaymentWebService {

    @WebMethod
    @WebResult(targetNamespace = "")
    CommonResult createPayment(Payment payment);
}
