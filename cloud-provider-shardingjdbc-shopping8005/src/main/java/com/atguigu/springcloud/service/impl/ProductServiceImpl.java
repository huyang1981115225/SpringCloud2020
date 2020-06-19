package com.atguigu.springcloud.service.impl;

import com.atguigu.springcloud.dao.ProductDao;
import com.atguigu.springcloud.entity.ProductDescript;
import com.atguigu.springcloud.entity.ProductInfo;
import com.atguigu.springcloud.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author huyang
 * @date 2020/6/18 16:28
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    @Transactional
    public void insertProductInfo(ProductInfo productInfo) {
        ProductDescript productDescript = new ProductDescript();
        // 添加商品信息
        productDao.insertProductInfo(productInfo);

        // 插入后可以得到主键
        productDescript.setProductInfoId(productInfo.getProductInfoId());
        productDescript.setStoreInfoId(productInfo.getStoreInfoId());
        productDescript.setDescript(productInfo.getDescript());
        // 添加商品描述
         productDao.insertProductDescript(productDescript);
    }

    @Override
    @Transactional
    public List<ProductInfo> queryProductList(int page, int pageSize) {
        int start = (page - 1) * pageSize;
        return productDao.queryProductList(start,pageSize);
    }

    @Override
    @Transactional
    public int selectCount() {
        return productDao.selectCount();
    }

    @Override
    @Transactional
    public List<Map> selectProductGroupList() {
        return productDao.selectProductGroupList();
    }
}
