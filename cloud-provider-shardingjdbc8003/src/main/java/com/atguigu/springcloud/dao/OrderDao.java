package com.atguigu.springcloud.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author huyang
 * @date 2020/6/17 15:05
 */
@Mapper
@Component
public interface OrderDao {

    /**
     * 插入订单
     * @param price
     * @param userId
     * @param status
     * @return
     */
    @Insert("insert into t_order(price,user_id,status) values (#{price},#{userId},#{status})")
    int insertOrder(@Param("price") BigDecimal price,
                    @Param("userId") Long userId,
                    @Param("status") String status);

    /**
     * 根据id查询
     * @return
     */
    @Select("<script>" +
            "select" +
            " * " +
            " from t_order t " +
            " where t.order_id in " +
            " <foreach collection='orderIds' open='(' separator=',' close=')' item='id'>" +
            " #{id} " +
            " </foreach>" +
            "</script>")
    List<Map> queryOrderByIds(@Param("orderIds") List<Long> orderIds);

    /**
     * 根据id和用户id查询
     * @return
     */
    @Select("<script>" +
            "select" +
            " * " +
            " from t_order t " +
            " where t.order_id in " +
            " <foreach collection='orderIds' open='(' separator=',' close=')' item='id'>" +
            " #{id} " +
            " </foreach>" +
            " and user_id = #{userId} " +
            "</script>")
    List<Map> queryOrderByIdsAndUserId(@Param("userId") Long userId,@Param("orderIds") List<Long> orderIds);
}
