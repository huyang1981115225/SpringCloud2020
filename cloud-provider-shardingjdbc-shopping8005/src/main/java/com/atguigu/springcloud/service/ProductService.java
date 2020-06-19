package com.atguigu.springcloud.service;

import com.atguigu.springcloud.entity.ProductInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author huyang
 * @date 2020/6/18 16:27
 */
public interface ProductService {

    /**
     * 添加商品信息
     * @param productInfo
     * @return
     */
    void insertProductInfo(ProductInfo productInfo);

    /**
     * 查询商品
     * @param start
     * @param pageSize
     * @return
     */
    List<ProductInfo> queryProductList(int page, int pageSize);

    /**
     * 商品总数统计
     * @return
     */
    int selectCount();

    /**
     * 商品分组统计
     * @return
     */
    List<Map> selectProductGroupList();
}
