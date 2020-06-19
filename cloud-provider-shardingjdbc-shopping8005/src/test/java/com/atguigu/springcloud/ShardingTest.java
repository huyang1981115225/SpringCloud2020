package com.atguigu.springcloud;

import com.atguigu.springcloud.entity.ProductInfo;
import com.atguigu.springcloud.service.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

/**
 * @author huyang
 * @date 2020/6/18 16:40
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ShoppingBootstrap.class})
public class ShardingTest {

    @Autowired
    private ProductService productService;

    @Test
    public void insertProduct(){
        for (int i = 1; i < 10 ; i++) {
            ProductInfo productInfo = new ProductInfo();
            productInfo.setStoreInfoId(2L);
            productInfo.setProductName("Java编程思想" + i);
            productInfo.setDescript("Java编程思想不错" + i);
            productInfo.setSpec("最新版");
            productInfo.setRegionCode("110100");
            productInfo.setPrice(200d);
            productService.insertProductInfo(productInfo);
        }
    }

    @Test
    public void queryProductList(){
        List<ProductInfo> result = productService.queryProductList(1, 10);
        System.out.println("查询结果： " + result);
    }

    @Test
    public void selectCount() {
        int num = productService.selectCount();
        System.out.println("商品总数： " + num);
    }

    @Test
    public void selectProductGroupList(){
        List<Map> result = productService.selectProductGroupList();
        System.out.println("商品分组统计： " + result);
    }
}
