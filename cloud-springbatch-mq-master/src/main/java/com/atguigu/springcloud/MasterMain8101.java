package com.atguigu.springcloud;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author huyang
 * @date 2020/7/13 16:12
 */
@SpringBootApplication
@EnableBatchProcessing
public class MasterMain8101 {

    public static void main(String[] args) {
        SpringApplication.run(MasterMain8101.class, args);
    }
}
