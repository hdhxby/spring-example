package org.springframework.context.expression;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.UnsatisfiedDependencyException;
import org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.annotation.QualifierAnnotationAutowireCandidateResolver;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.ContextAnnotationAutowireCandidateResolver;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.ClassPathResource;
import x.y.z.manager.impl.FooManagerImpl;

import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * ${}测试
 */
public class StandardBeanExpressionResolverTest {
    private static final Logger logger = LoggerFactory.getLogger(StandardBeanExpressionResolverTest.class);

    DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
    @BeforeEach
    public void setup() {
        // BeanPostProcessor, 依赖注入
        AutowiredAnnotationBeanPostProcessor autowiredAnnotationBeanPostProcessor = new AutowiredAnnotationBeanPostProcessor();
        autowiredAnnotationBeanPostProcessor.setBeanFactory(beanFactory);
        beanFactory.addBeanPostProcessor(autowiredAnnotationBeanPostProcessor);

        // 表达式解析
        beanFactory.setAutowireCandidateResolver(new ContextAnnotationAutowireCandidateResolver());
        // BeanFactoryPostProcessor, 表达式替换
        PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
        propertySourcesPlaceholderConfigurer.setEnvironment(new StandardEnvironment());
        propertySourcesPlaceholderConfigurer.postProcessBeanFactory(beanFactory);
    }

    /**
     * 依赖注入
     * 没有表达式解析
     * 报错
     */
    @Test
    public void test3() {
        System.setProperty("foo","foo");
        beanFactory.registerBeanDefinition("fooManager", new AnnotatedGenericBeanDefinition(FooManagerImpl.class));

        beanFactory.setBeanExpressionResolver(new StandardBeanExpressionResolver());

        // 没有表达式解析
        Assertions.assertEquals("foo", beanFactory.getBean(FooManagerImpl.class).getFoo());
    }
}
