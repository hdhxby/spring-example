package org.springframework.context.annotation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.expression.StandardBeanExpressionResolver;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.StandardEnvironment;
import x.y.z.config.FooConfiguration;
import x.y.z.repository.FooRepository;
import x.y.z.service.FooService;
import x.y.z.service.impl.FooServiceImpl;

import static org.junit.jupiter.api.Assertions.*;

public class ConfigurationClassPostProcessorTest {
    DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
    @BeforeEach
    public void setup() {
        // BeanPostProcessor, 依赖注入
        AutowiredAnnotationBeanPostProcessor autowiredAnnotationBeanPostProcessor = new AutowiredAnnotationBeanPostProcessor();
        autowiredAnnotationBeanPostProcessor.setBeanFactory(beanFactory);
        beanFactory.addBeanPostProcessor(autowiredAnnotationBeanPostProcessor);

        // 表达式解析
        beanFactory.setAutowireCandidateResolver(new ContextAnnotationAutowireCandidateResolver());
        beanFactory.setBeanExpressionResolver(new StandardBeanExpressionResolver());
        // BeanFactoryPostProcessor, 表达式替换
        PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
        propertySourcesPlaceholderConfigurer.setEnvironment(new StandardEnvironment());
        propertySourcesPlaceholderConfigurer.postProcessBeanFactory(beanFactory);
    }

    @Test
    public void testClassPathBeanDefinitionScanner(){
        beanFactory.registerBeanDefinition("fooConfiguration", new AnnotatedGenericBeanDefinition(FooConfiguration.class));

        ConfigurationClassPostProcessor configurationClassPostProcessor = new ConfigurationClassPostProcessor();
        configurationClassPostProcessor.postProcessBeanDefinitionRegistry(beanFactory);
        configurationClassPostProcessor.postProcessBeanFactory(beanFactory);

        assertNotNull(beanFactory.getBean("fooServiceImpl"));
        assertTrue(beanFactory.getBean("fooServiceImpl") instanceof FooService);
        assertNotNull(beanFactory.getBean("fooRepositoryImpl"));
        assertTrue(beanFactory.getBean("fooRepositoryImpl") instanceof FooRepository);
        // 依赖注入
        assertNotNull(beanFactory.getBean("fooServiceImpl",FooServiceImpl.class).getFooRepository());
    }
}
