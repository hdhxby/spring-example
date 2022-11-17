package org.springframework.validation.beanvalidation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.validation.DirectFieldBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.SmartValidator;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.beanvalidation.BeanValidationPostProcessor;
import x.y.z.bean.Foo;

import static org.junit.jupiter.api.Assertions.*;

public class BeanValidationPostProcessorTest {

    @Test
    public void test(){
        BeanValidationPostProcessor beanValidationPostProcessor = new BeanValidationPostProcessor();
        beanValidationPostProcessor.afterPropertiesSet();
        assertThrowsExactly(BeanInitializationException.class, () -> beanValidationPostProcessor.doValidate(new Foo()));
    }

    @Test
    public void test2(){
        SmartValidator smartValidator = new CustomValidatorBean();
        Foo foo =new Foo();
        Errors errors = new DirectFieldBindingResult(foo, "foo");
        ValidationUtils.invokeValidator(smartValidator, foo, errors);
        System.out.println(errors);
    }
}
