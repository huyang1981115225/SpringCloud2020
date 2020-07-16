package com.atguigu.springcloud.utils;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

/**
 * @author huyang
 * @date 2019-12-13 15:53
 * @Description http工具类
 */
public class HttpClientUtils {

    public String postXml(String uri, String xml, String charset) {

        String result = null;
        CloseableHttpResponse httpResponse = null;
        // 判断字符集是否为空，为空则设置
        if (charset == null || charset.isEmpty()) {
            charset = "utf-8";
        }
        // 获取Http客户端
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        // 创建请求对象
        HttpPost httpPost = new HttpPost(uri);

        try {
            httpPost.setHeader("Content-Type", "text/xml; charset=UTF-8");
            StringEntity entityParams = new StringEntity(xml, charset);
            // 将请求对象封装到POST对象中
            httpPost.setEntity(entityParams);

            // 发送请求
            httpResponse = httpClient.execute(httpPost);

            // 判断请求是否成功
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                System.out.println("Http 发送请求成功");
                result = EntityUtils.toString(httpResponse.getEntity(), charset);
            } else {
                System.out.println("Http 发送请求失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
