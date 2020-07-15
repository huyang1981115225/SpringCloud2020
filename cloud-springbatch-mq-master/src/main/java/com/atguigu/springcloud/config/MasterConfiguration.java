package com.atguigu.springcloud.config;

import com.atguigu.springcloud.support.BatchIncrementer;
import com.atguigu.springcloud.support.JobListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.partition.PartitionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * @author huyang
 * @date 2020/7/13 16:13
 */
@Configuration
public class MasterConfiguration {

    // 创建任务对象的对象
    @Autowired
    JobBuilderFactory jobBuilderFactory;

    // 创建Step对象的对象   任务的执行由Step决定
    @Autowired
    StepBuilderFactory stepBuilderFactory;

    @Autowired
    EntityManagerFactory entityManagerFactory;

    @Profile({"master", "mixed"})
    @Bean
    public Job job(@Qualifier("masterStep") Step masterStep) {
        return jobBuilderFactory.get("endOfDayjob")
                .start(masterStep)
                .incrementer(new BatchIncrementer())
                .listener(new JobListener())
                .build();
    }

    @Bean("masterStep")
    public Step masterStep( Step slaveStep,
                           PartitionHandler partitionHandler,
                           DataSource dataSource) {
        return stepBuilderFactory.get("masterStep")
                .partitioner(slaveStep.getName(), new ColumnRangePartitioner(dataSource))
                .step(slaveStep)
                .partitionHandler(partitionHandler)
                .build();
    }
}

