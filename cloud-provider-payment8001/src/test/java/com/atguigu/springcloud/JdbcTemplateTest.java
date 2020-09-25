package com.atguigu.springcloud;

import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author huyang
 * @date 2020/8/7 14:19
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class JdbcTemplateTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PaymentService paymentService;

    @Test
    /**
     * 简单查询
     */
    public void simpleQuery() {
        String serial = jdbcTemplate.queryForObject("SELECT SERIAL FROM PAYMENT WHERE ID = ?  ", new Object[]{5}, java.lang.String.class);
        System.out.println(serial);
    }

    @Test
    /**
     * queryForObject查询单行 查询将查询值返回给对象
     */
    public void queryToObject() {
        String sql = "select id,name,birth,sal from hy_test where id = ?";
        RowMapper<Payment> rowMapper = new BeanPropertyRowMapper<Payment>(Payment.class);
        Payment payment = jdbcTemplate.queryForObject(sql, rowMapper, "10002");//最后一个参数为id值
        System.out.println(payment);
    }

    @Test
    /**
     * queryForObject查询查询多行 查询将查询值返回给对象
     */
    public void queryToObjectList() {
        String sql = "SELECT ID,SERIAL FROM PAYMENT where id > ?";
        RowMapper<Payment> rowMapper = new BeanPropertyRowMapper<Payment>(Payment.class);
        List<Payment> paymentList = jdbcTemplate.query(sql, rowMapper, 2);//最后一个参数为id值
        System.out.println(paymentList);
    }

    @Test
    /**
     * 支持in
     */
    public void queryIn() {
        String sql = "SELECT ID,SERIAL FROM PAYMENT where id in (:id)  and name like :name and SERIAL = :serial";
        Map<String, Object> map = new LinkedHashMap<>();
        List<Integer> ids = new ArrayList<>();
        ids.add(2);
        ids.add(3);
        ids.add(5);
        ids.add(6);

        map.put("id", ids);
        map.put("name", "%" + "Tom" + "%");
        map.put("serial", "传智黑马");
        RowMapper<Payment> rowMapper = new BeanPropertyRowMapper<Payment>(Payment.class);
        List<Payment> userList = new NamedParameterJdbcTemplate(jdbcTemplate).query(sql, map, rowMapper);//最后一个参数为id值
        System.out.println(userList);
    }

    @Test
    /**
     * 支持like
     */
    public void queryLike() {
        String sql = "SELECT ID,SERIAL FROM PAYMENT where name like ?";//目标sql: SELECT * FROM table_in WHERE LIKE '%abc%'
        String name = "Tom";
        RowMapper<Payment> rowMapper = new BeanPropertyRowMapper<Payment>(Payment.class);
        List<Payment> userList = jdbcTemplate.query(sql, rowMapper, "%" + name + "%");
        System.out.println(userList);

    }

    @Test
    /**
     * 支持批量插入
     */
    @Transactional
    public void batchInsert() {
        //演示批量插入，TODO jdbcTemplate.batchUpdate 异常不会回滚
        List<Object[]> payments = new ArrayList<>();
        payments.add(new Object[]{"Python", "user4"});
        payments.add(new Object[]{"Java", "user1"});
        payments.add(new Object[]{"Php", "user2"});
        payments.add(new Object[]{"C++", "user3"});// TODO 这里故意制造异常

        int[] result = jdbcTemplate.batchUpdate("insert into payment(serial,name) values (?,?)", payments);
        System.err.println("Result length:  " + result.length);
        System.err.println("Result  :" + Arrays.toString(result));

    }

    @Test
    public void testTransactional(){
        Integer money = 10;
        Integer account = 1;
        paymentService.updateRecord(money,account);
    }
}
