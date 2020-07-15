package com.atguigu.springcloud.support;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersIncrementer;

import java.util.Date;

/**
 * @author huyang
 * @date 2020/7/13 16:31
 */
public class BatchIncrementer implements JobParametersIncrementer {

    @Override
    public JobParameters getNext(JobParameters parameters){
        return new JobParametersBuilder()
                .addLong("batchDate",new Date().getTime())
                .toJobParameters();
    }
}
