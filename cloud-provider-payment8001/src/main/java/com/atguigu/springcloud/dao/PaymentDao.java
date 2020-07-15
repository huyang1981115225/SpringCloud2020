package com.atguigu.springcloud.dao;

import com.atguigu.springcloud.entities.Payment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author huyang
 * @date 2020/5/16 12:10
 */
@Mapper
public interface PaymentDao {

    int create(Payment payment);

    int update(Payment payment);

    Payment getPaymentById(@Param("id") Long id);

    int delete(@Param("id") Long id);
}
