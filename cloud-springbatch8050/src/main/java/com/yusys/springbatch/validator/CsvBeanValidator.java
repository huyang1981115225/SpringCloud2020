package com.yusys.springbatch.validator;

import org.springframework.batch.item.validator.ValidationException;
import org.springframework.batch.item.validator.Validator;
import org.springframework.beans.factory.InitializingBean;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * 定义校验器：使用JSR-303(hibernate-validator)注解，
 * 来校验ItemReader读取到的数据是否满足要求。如不满足则不会进行接下来的批处理任务。
 *
 * @author huyang
 * @date 2020/7/27 15:20
 * @description
 * 定义校验器：使用JSR-303(hibernate-validator)注解，来校验ItemReader读取到的数据是否满足要求。
 */
public class CsvBeanValidator<T> implements Validator<T>, InitializingBean {

    private javax.validation.Validator validator;

    /**
     * 进行JSR-303的Validator的初始化
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.usingContext().getValidator();
    }

    /**
     * 使用validator方法检验数据
     * @param value
     * @throws ValidationException
     */
    @Override
    public void validate(T value) throws ValidationException {
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(value);
        if (constraintViolations.size() > 0) {
            StringBuilder message = new StringBuilder();
            for (ConstraintViolation<T> constraintViolation: constraintViolations) {
                message.append(constraintViolation.getMessage() + "\n");
            }
            throw new ValidationException(message.toString());
        }
    }
}
