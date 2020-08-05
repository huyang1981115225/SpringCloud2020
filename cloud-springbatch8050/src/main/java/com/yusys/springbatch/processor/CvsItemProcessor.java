package com.yusys.springbatch.processor;

import com.yusys.springbatch.domain.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.validator.ValidatingItemProcessor;
import org.springframework.batch.item.validator.ValidationException;

/**
 * @author huyang
 * @date 2020/7/27 15:18
 */
@Slf4j
public class CvsItemProcessor extends ValidatingItemProcessor<Person> {


    @Override
    public Person process(Person item) throws ValidationException {
        // 执行super.process()才能调用自定义的校验器
        log.info("processor start validating...");
        super.process(item);

        // 数据处理，比如将中文性别设置为M/F
        if ("男".equals(item.getGender())) {
            item.setGender("M");
        } else {
            item.setGender("F");
        }
        log.info("processor end validating...");
        return item;
    }
}
