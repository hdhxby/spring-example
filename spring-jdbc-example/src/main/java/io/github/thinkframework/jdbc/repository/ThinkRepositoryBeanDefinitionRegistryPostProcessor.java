package io.github.thinkframework.jdbc.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.util.Arrays;

/**
 * 处理Bean代理
 */
public class ThinkRepositoryBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {

    private static final Logger log = LoggerFactory.getLogger(ThinkRepositoryBeanDefinitionRegistryPostProcessor.class);

    private String basePackage;

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        // 解析自定义注解
        ThinkRepositoryClassPathBeanDefinitionScanner beanDefinitionScanner = new ThinkRepositoryClassPathBeanDefinitionScanner(registry);
        beanDefinitionScanner.addIncludeFilter(new AnnotationTypeFilter(ThinkRepository.class));
        beanDefinitionScanner.scan(basePackage);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();
        Arrays.stream(beanDefinitionNames).forEach(beanDefinitionName -> {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanDefinitionName);
            if(beanDefinition instanceof ScannedGenericBeanDefinition){
                ScannedGenericBeanDefinition scannedGenericBeanDefinition = (ScannedGenericBeanDefinition) beanDefinition;
                if(scannedGenericBeanDefinition.getMetadata().hasAnnotation(ThinkRepository.class.getName())){
                    String beanClassName =  scannedGenericBeanDefinition.getBeanClassName();
                    scannedGenericBeanDefinition.setBeanClass(ThinkRepositoryFactoryBean.class);
                    scannedGenericBeanDefinition.getPropertyValues().add("beanClazzName",beanClassName);
                }
            }
        });
    }

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }
}
