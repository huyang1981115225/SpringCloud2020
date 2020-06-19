package com.atguigu.springcloud;

import com.atguigu.springcloud.dao.OrderDao;
import com.atguigu.springcloud.ShardingJabcSimpleBootstrap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 测试 sharding-JDBC 水平分库分表
 * @author huyang
 * @date 2020/6/17 15:11
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ShardingJabcSimpleBootstrap.class})
public class OrderDaoTest {

    @Autowired
    private OrderDao orderDao;

    @Test
    public void insertOrder() {
        for (int i = 2; i < 20; i++) {
            orderDao.insertOrder(new BigDecimal(i), 4L, "SUCCESS");
        }
    }

    /**
     * 如果既分库又分表了，没有传入分片键的时候，会产生笛卡尔积，同时路由到多个库
     */
    @Test
    public void queryOrderByIds() {
        List<Long> orderIds = new ArrayList<>();
        orderIds.add(480057309905551361L);
        List<Map> result = orderDao.queryOrderByIds(orderIds);
        System.out.println("查询结果： " + result);
    }

    /**
     * 如果既分库又分表了，传入分片键
     */
    @Test
    public void queryOrderByIdsAndUserId() {
        List<Long> orderIds = new ArrayList<>();
        orderIds.add(480057309905551361L);
        List<Map> result = orderDao.queryOrderByIdsAndUserId(4L,orderIds);
        System.out.println("查询结果： " + result);
    }
}
