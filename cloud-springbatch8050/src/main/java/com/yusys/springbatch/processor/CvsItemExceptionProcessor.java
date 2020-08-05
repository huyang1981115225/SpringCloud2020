package com.yusys.springbatch.processor;

import com.yusys.springbatch.domain.Person;
import com.yusys.springbatch.exception.RetryException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.validator.ValidatingItemProcessor;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.stereotype.Component;

/**
 * @author huyang
 * @date 2020/7/27 15:18
 */
@Slf4j
@Component
public class CvsItemExceptionProcessor implements ItemProcessor<Person, Person> {

    private int attempCount = 0;

    @Override
    public Person process(Person item) throws Exception {
        log.info("processorException start ...");
        System.out.println("processing item:  " + item);
        // 数据处理，比如将中文性别设置为M/F
        if ("男".equals(item.getGender())) {
            attempCount++;
            System.out.println("Retried " + attempCount + " times success.");
        } else if ("女".equals(item.getGender())) {
            attempCount++;
            System.out.println("Retried " + attempCount + " times success.");
        } else {
            System.out.println("Processor the " + attempCount + " times fail.");
            throw new RetryException("性别有误");
        }
        log.info("processorException end ...");
        return item;
    }
}
