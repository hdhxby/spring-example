package io.github.hdhxby.example.factory.config;

import io.github.hdhxby.example.entity.Hair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;

public class ImportBeanPostProcessor implements BeanPostProcessor {

    private static final Logger log = LoggerFactory.getLogger(ImportBeanPostProcessor.class);

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
//        log.info("postProcessBeforeInitialization");
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
//        log.info("postProcessAfterInitialization");
        return bean;
    }

    @Bean
    public Hair hair(){
        return new Hair();
    }
}
