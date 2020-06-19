package com.atguigu.springcloud.entity;

import lombok.Data;

/**
 * 商品基本信息
 * @author huyang
 * @date 2020/6/18 15:31
 */
@Data
public class ProductInfo {

    private Long productInfoId;

    /**
     * 所属店铺id
     */
    private Long storeInfoId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 规格
     */
    private String spec;

    /**
     * 产地
     */
    private String regionCode;

    /**
     * 商品价格
     */
    private Double price;

    /**
     * 商品图片
     */
    private String imageUrl;

    /**
     * 商品描述
     */
    private  String descript;

    /**
     * 产地名称
     */
    private String placeOfOrigin;

    /**
     * 店铺名称
     */
    private String storeName;
}
