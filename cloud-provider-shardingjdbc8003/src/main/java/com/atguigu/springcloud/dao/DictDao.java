package com.atguigu.springcloud.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @author huyang
 * @date 2020/6/18 10:09
 */
@Component
@Mapper
public interface DictDao {

    /**
     * 新增字典
     * @param dictId
     * @param type
     * @param code
     * @param value
     * @return
     */
    @Insert("insert into t_dict(dict_id,type,code,value) values (#{dictId},#{type},#{code},#{value})")
    int insertDict(@Param("dictId") Long dictId,
                   @Param("type") String type,
                   @Param("code") String code,
                   @Param("value") String value);

    /**
     * 删除字典
     * @param dictId
     * @return
     */
    @Delete("delete from t_dict where dict_id = #{dictId}")
    int deleteDict(@Param("dictId") Long dictId);
}
