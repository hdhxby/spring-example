package com.hdhxby.example.factory.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;

/**
 * 通过api手动注册
 */
public class ApiBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {

    private static final Logger log = LoggerFactory.getLogger(ApiBeanDefinitionRegistryPostProcessor.class);


    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        log.debug("第一个PostProcessor初始化");
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        log.debug("第一个PostProcessor初始化");
//        ((AbstractApplicationContext) registry).addBeanFactoryPostProcessor(new CustomeBeanDefinitionRegistryPostProcessor());
//        ((AbstractApplicationContext) registry).addBeanFactoryPostProcessor(new CustomeBeanFactoryPostProcessor());
    }
}
