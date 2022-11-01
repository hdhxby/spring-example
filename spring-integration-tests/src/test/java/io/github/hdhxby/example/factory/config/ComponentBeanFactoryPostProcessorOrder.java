package io.github.hdhxby.example.factory.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

@Component
public class ComponentBeanFactoryPostProcessorOrder implements BeanFactoryPostProcessor, Ordered {

    private static final Logger log = LoggerFactory.getLogger(ComponentBeanFactoryPostProcessorOrder.class);

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        log.debug("第四Processor个初始化");
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
