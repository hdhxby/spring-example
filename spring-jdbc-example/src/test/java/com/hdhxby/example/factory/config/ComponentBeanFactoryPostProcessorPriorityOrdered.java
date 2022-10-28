package com.hdhxby.example.factory.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.PriorityOrdered;
import org.springframework.stereotype.Component;

@Component
public class ComponentBeanFactoryPostProcessorPriorityOrdered implements BeanFactoryPostProcessor, PriorityOrdered {

    private static final Logger log = LoggerFactory.getLogger(ComponentBeanFactoryPostProcessorPriorityOrdered.class);

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        log.debug("第三PostProcessor个初始化");
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
