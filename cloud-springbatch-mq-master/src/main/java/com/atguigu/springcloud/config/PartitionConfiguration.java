package com.atguigu.springcloud.config;

import com.atguigu.springcloud.entity.Article;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.partition.PartitionHandler;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.integration.partition.MessageChannelPartitionHandler;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.orm.JpaNativeQueryProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.scheduling.support.PeriodicTrigger;

import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据分区
 *
 * @author huyang
 * @date 2020/7/13 16:33
 */
@Configuration
public class PartitionConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public JobExplorer jobExplorer;

    @Autowired
    public JobRepository jobRepository;

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    EntityManagerFactory entityManagerFactory;

    private static final int GRID_SIZE = 10;

    @Bean
    public PartitionHandler partitionHandler(MessagingTemplate messagingTemplate) throws Exception {
        MessageChannelPartitionHandler partitionHandler = new MessageChannelPartitionHandler();
        partitionHandler.setStepName("slaveStep");
        partitionHandler.setGridSize(GRID_SIZE);
        partitionHandler.setMessagingOperations(messagingTemplate);
        partitionHandler.setPollInterval(5000l);
        partitionHandler.setJobExplorer(jobExplorer);
        partitionHandler.afterPropertiesSet();
        return partitionHandler;
    }

    /**
     * 从节点最关键的地方在于StepExecutionRequestHandler，
     * 他会接收MQ消息中间件中的消息，并从分区信息中获取到需要处理的数据边界，
     * 如下ItemReader
     */
    @Bean(destroyMethod = "")
    @StepScope
    public JpaPagingItemReader<Article> jpaPagingItemReader(
            @Value("#{stepExecutionContext['minValue']}") Long minValue,
            @Value("#{stepExecutionContext['maxValue']}") Long maxValue) {
        System.err.println("接收到分片参数["+minValue+"->"+maxValue+"]");
        JpaPagingItemReader<Article> reader = new JpaPagingItemReader<>();
        JpaNativeQueryProvider queryProvider = new JpaNativeQueryProvider<>();
        String sql = "select * from kl_article where  arcid >= :minValue and arcid <= :maxValue";
        queryProvider.setSqlQuery(sql);
        queryProvider.setEntityClass(Article.class);
        reader.setQueryProvider(queryProvider);
        Map queryParames= new HashMap();
        queryParames.put("minValue",minValue);
        queryParames.put("maxValue",maxValue);
        reader.setParameterValues(queryParames);
        reader.setEntityManagerFactory(entityManagerFactory);
        return  reader;
    }

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata defaultPoller() {
        PollerMetadata pollerMetadata = new PollerMetadata();
        pollerMetadata.setTrigger(new PeriodicTrigger(10));
        return pollerMetadata;
    }
}

