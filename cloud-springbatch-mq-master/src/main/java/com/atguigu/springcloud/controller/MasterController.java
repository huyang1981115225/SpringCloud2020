package com.atguigu.springcloud.controller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author huyang
 * @date 2020/7/13 16:38
 */
@RestController
@RequestMapping("master")
public class MasterController {@Autowired
private JobLauncher jobLauncher;

    @Autowired
    private Job job;

    @RequestMapping("/runJob")
    public String jobRun() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {

        // TODO 把参数传给任务
        JobParameters parameters = new JobParametersBuilder().addDate("master start time",new Date()).toJobParameters();

        // TODO 启动任务，并把参数传给任务
        jobLauncher.run(job,parameters);
        return "master start success";
    }
}

