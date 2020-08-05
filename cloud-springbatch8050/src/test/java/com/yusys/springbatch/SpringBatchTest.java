package com.yusys.springbatch;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author huyang
 * @date 2020/7/27 15:49
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBatchTest {

    @Autowired
    SimpleJobLauncher jobLauncher;

    @Autowired
    Job importJob;

    @Test
    public void test() throws Exception {
        // 后置参数：使用JobParameters中绑定参数
        JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis())
                .toJobParameters();
        jobLauncher.run(importJob, jobParameters);
    }
}
