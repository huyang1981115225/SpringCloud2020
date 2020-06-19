package com.atguigu.springcloud.entity;

import lombok.Data;

/**
 * 店铺基本信息
 * @author huyang
 * @date 2020/6/18 15:32
 */
@Data
public class StoreInfo {

    private Long id;

    /**
     * 店铺名称
     */
    private String storeName;

    /**
     * 信誉等级
     */
    private int reputation;

    /**
     * 店铺所在地
     */
    private String regionCode;
}
