package io.github.thinkframework.jdbc.repository;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

public class ThinkRepositoryImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry, BeanNameGenerator importBeanNameGenerator) {
        registerBeanDefinitions(importingClassMetadata, registry);
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        // 此处会注册Bean到registry
        registry.registerBeanDefinition("thinkRepositoryBeanDefinitionRegistryPostProcessor",BeanDefinitionBuilder
                .genericBeanDefinition(ThinkRepositoryBeanDefinitionRegistryPostProcessor.class)
                .addPropertyValue("basePackage",importingClassMetadata.getAnnotationAttributes(ThinkRepositoryScan.class.getName())
                        .get("value").toString())
                .getBeanDefinition());

        registry.registerBeanDefinition("thinkRepositoryBeanPostProcessor",BeanDefinitionBuilder
                .genericBeanDefinition(ThinkRepositoryBeanPostProcessor.class)
                .getBeanDefinition());
    }
}
