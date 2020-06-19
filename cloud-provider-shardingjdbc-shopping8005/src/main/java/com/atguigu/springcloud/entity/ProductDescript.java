package com.atguigu.springcloud.entity;

import lombok.Data;

/**
 * 商品描述信息
 * @author huyang
 * @date 2020/6/18 15:26
 */
@Data
public class ProductDescript {

    private Long id;

    /**
     * 所屬商品id
     */
    private Long productInfoId;

    /**
     * 商品描述
     */
    private String descript;

    /**
     * 所属店铺id
     */
    private Long storeInfoId;
}
