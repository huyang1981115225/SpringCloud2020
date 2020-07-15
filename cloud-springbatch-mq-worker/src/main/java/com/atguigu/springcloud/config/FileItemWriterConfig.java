package com.atguigu.springcloud.config;

import com.atguigu.springcloud.entity.Article;
import com.atguigu.springcloud.item.CommonPartitionFileItemWriter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

/**
 * 将读取的数据写入到文件
 *
 * Created by huyang on 2019/10/11.
 */
@Configuration
public class FileItemWriterConfig {

    @Bean
    public FlatFileItemWriter<Article> fileItemWriter() throws Exception {

        // TODO 把Article对象转换成字符串输出到文件
        FlatFileItemWriter<Article> writer = new FlatFileItemWriter<>();
        String path = "D:\\fubon\\工作记录\\article.txt";
        writer.setResource(new FileSystemResource(path));

        writer.setAppendAllowed(true);

        // TODO 把Article对象转换成字符串
        writer.setLineAggregator(new LineAggregator<Article>() {

            ObjectMapper mapper = new ObjectMapper();

            @Override
            public String aggregate(Article item) {

                String str = null;
                try {
                    str = mapper.writeValueAsString(item);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                return str;
            }
        });
        writer.afterPropertiesSet();;
        return writer;
    }

    @Bean
    @StepScope
    public CommonPartitionFileItemWriter commonPartitionFileItemWriter(@Value("#{stepExecutionContext[minValue]}") final String minValue,
                                                                       @Value("#{stepExecutionContext[maxValue]}") final String maxValue) {
        return new CommonPartitionFileItemWriter(Article.class,minValue,maxValue);
    }
}
