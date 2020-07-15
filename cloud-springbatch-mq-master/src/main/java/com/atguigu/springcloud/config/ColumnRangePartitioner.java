package com.atguigu.springcloud.config;

import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 根据数据ID分片
 *
 * @author huyang
 * @date 2020/7/13 16:28
 */
public class ColumnRangePartitioner implements Partitioner {

    private JdbcOperations jdbcTemplate;

    ColumnRangePartitioner(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {
        System.out.println("开始创建分区...");
        int min = jdbcTemplate.queryForObject("SELECT MIN(arcid) from  kl_article", Integer.class);
        int max = jdbcTemplate.queryForObject("SELECT MAX(arcid) from  kl_article", Integer.class);
        int targetSize = (max - min) / gridSize + 1;
        Map<String, ExecutionContext> result = new HashMap<String, ExecutionContext>();
        int number = 0;
        int start = min;
        int end = start + targetSize - 1;

        while (start <= max) {
            ExecutionContext value = new ExecutionContext();
            result.put("partition" + number, value);

            if (end >= max) {
                end = max;
            }
            value.putInt("minValue", start);
            value.putInt("maxValue", end);
            start += targetSize;
            end += targetSize;
            number++;
        }

        System.out.println("创建分区完成:   " + result);
        return result;
    }
}

