package com.atguigu.springcloud.entity;

import lombok.Data;

/**
 * 地理区域信息
 * @author huyang
 * @date 2020/6/18 15:33
 */
@Data
public class Region {

    private Long id;

    /**
     * 地理区域编码
     */
    private String regionCode;

    /**
     * 地理区域名称
     */
    private String regionName;

    /**
     * 地理区域级别
     */
    private int level;

    /**
     * 上级地理区域编码
     */
    private String parentRegionCode;
}
