package com.atguigu.springcloud.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author huyang
 * @date 2020/6/17 17:13
 */
@Mapper
@Component
public interface UserDao {

    /**
     * 新增用户
     * @param userId
     * @param fullname
     * @return
     */
    @Insert("insert into t_user(user_id,fullname) values (#{userId},#{fullname})")
    int insertUser(@Param("userId") Long userId, @Param("fullname") String fullname);

    /**
     * 根据id查询用户
     * @param userId
     * @param orderIds
     * @return
     */
    @Select("<script>" +
            "select" +
            " * " +
            " from t_user t " +
            " where t.user_id in " +
            " <foreach collection='userIds' open='(' separator=',' close=')' item='id'>" +
            " #{id} " +
            " </foreach>" +
            "</script>")
    List<Map> queryOrderByIdsAndUserId(@Param("userIds") List<Long> userIds);

    /**
     * 根据id查询用户并关联公共表t_dict
     * @param userId
     * @param orderIds
     * @return
     */
    @Select("<script>" +
            "select" +
            " * " +
            " from t_user t,t_dict b " +
            " where t.user_type = b.code and t.user_id in " +
            " <foreach collection='userIds' open='(' separator=',' close=')' item='id'>" +
            " #{id} " +
            " </foreach>" +
            "</script>")
    List<Map> queryUserInfoByIds(@Param("userIds") List<Long> userIds);
}
