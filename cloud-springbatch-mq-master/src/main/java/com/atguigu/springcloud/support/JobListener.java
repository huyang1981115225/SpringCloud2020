package com.atguigu.springcloud.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

/**
 * job执行监听器
 *
 * @author huyang
 * @date 2020/7/13 16:31
 */
public class JobListener implements JobExecutionListener {

    Logger logger= LoggerFactory.getLogger(getClass());

    @Override
    public void beforeJob(JobExecution jobExecution) {
        logger.error("Job准备执行,JobName:{}",jobExecution.getJobInstance().getJobName());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {

        logger.error("EndOfDayJob执行完毕,执行结果:{}",jobExecution.getExitStatus());
    }
}
