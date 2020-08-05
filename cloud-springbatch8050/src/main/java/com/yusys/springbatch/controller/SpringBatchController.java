package com.yusys.springbatch.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author huyang
 * @date 2020/7/27 15:26
 */
@RestController
// lombok 日志
@Slf4j
@RequestMapping("/springbatch")
public class SpringBatchController {

    @Autowired
    SimpleJobLauncher jobLauncher;

    @Autowired
    Job importJob;

    @GetMapping("/runjob")
    public String runJob() throws Exception {
        // 后置参数：使用JobParameters中绑定参数
        JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis())
                .toJobParameters();
        jobLauncher.run(importJob, jobParameters);
        return "SUCCESS";
    }
}
