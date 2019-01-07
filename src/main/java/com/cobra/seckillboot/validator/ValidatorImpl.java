package com.cobra.seckillboot.validator;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;


/**
 * @Author: Baron
 * @Description:
 * @Date: Created in 2018/12/30 19:59
 */
@Component
public class ValidatorImpl implements InitializingBean {

    private Validator validator;

    //实现校验方法返回结果
    public ValidationResult validate(Object bean) {
        final ValidationResult result = new ValidationResult();
        Set<ConstraintViolation<Object>> constraintViolationSet =validator.validate(bean);
        if(constraintViolationSet.size()>0)  {
            //有错误
            result.setHasErrors(true);
            constraintViolationSet.forEach(constraintViolation -> {
                String errMsg= constraintViolation.getMessage();
                String propertyName = constraintViolation.getPropertyPath().toString();
                result.getErrorMsgMap().put(propertyName,errMsg);
            });
//            for (ConstraintViolation constraintViolation: constraintViolationSet) {
//                String errMsg= constraintViolation.getMessage();
//                Path path = constraintViolation.getPropertyPath();
//                String propertyName = path.toString();
//                result.getErrorMsgMap().put(propertyName,errMsg);
//            }
        }
        return result;
    };

    @Override
    public void afterPropertiesSet() throws Exception {
        //将hibernate validator通过工厂的初始化方式使其实例化
        this.validator=Validation.buildDefaultValidatorFactory().getValidator();
    }
}
