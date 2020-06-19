package com.atguigu.springcloud;

import com.atguigu.springcloud.dao.UserDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 测试 sharding-JDBC 垂直分库
 * @author huyang
 * @date 2020/6/17 15:11
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ShardingJabcSimpleBootstrap.class})
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Test
    public void insertUser() {
        for (int i = 0; i < 10; i++) {
            Long id = i + 1L ;
            userDao.insertUser(id,"姓名" + id);
        }
    }

    /**
     * 如果既分库又分表了，没有传入分片键的时候，会产生笛卡尔积，同时路由到多个库
     */
    @Test
    public void queryUserByIds() {
        List<Long> userIds = new ArrayList<>();
        userIds.add(1L);
        userIds.add(2L);
        List<Map> result = userDao.queryOrderByIdsAndUserId(userIds);
        System.out.println("查询结果： " + result);
    }

    @Test
    public void queryUserInfoByIds(){
        List<Long> userIds = new ArrayList<>();
        userIds.add(1L);
        userIds.add(2L);
        List<Map> result = userDao.queryUserInfoByIds(userIds);
        System.out.println("查询结果： " + result);
    }
}
