package com.atguigu.springcloud;

import com.atguigu.springcloud.dao.DictDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author huyang
 * @date 2020/6/18 10:14
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ShardingJabcSimpleBootstrap.class})
public class DictDaoTest {

    @Autowired
    private DictDao dictDao;

    @Test
    public void insertDict(){
        dictDao.insertDict(1L,"user_type","0","管理员");
        dictDao.insertDict(2L,"user_type","1","操作员");
    }

    @Test
    public void deleteDict(){
        dictDao.deleteDict(1L);
        dictDao.deleteDict(2L);
    }
}
