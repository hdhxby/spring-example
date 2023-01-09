package org.springframework.beans.factory.annotation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.UnsatisfiedDependencyException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.ClassPathResource;
import x.y.z.manager.impl.FooManagerImpl;

import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * ${}测试
 */
public class QualifierAnnotationAutowireCandidateResolverTest {
    private static final Logger logger = LoggerFactory.getLogger(QualifierAnnotationAutowireCandidateResolverTest.class);


    private DefaultListableBeanFactory defaultListableBeanFactory;

    @BeforeEach
    public void setup() {
        defaultListableBeanFactory = new DefaultListableBeanFactory();
        // 依赖注入
        AutowiredAnnotationBeanPostProcessor autowiredAnnotationBeanPostProcessor = new AutowiredAnnotationBeanPostProcessor();
        autowiredAnnotationBeanPostProcessor.setBeanFactory(defaultListableBeanFactory);
        defaultListableBeanFactory.addBeanPostProcessor(autowiredAnnotationBeanPostProcessor);

        System.setProperty("foo","foo");
    }

    /**
     * 依赖注入
     * 没有表达式解析
     * 报错
     */
    @Test
    public void testWithOutQualifierAnnotationAutowireCandidateResolver() {
        defaultListableBeanFactory.registerBeanDefinition("fooManagerImpl", new AnnotatedGenericBeanDefinition(FooManagerImpl.class));
        // 没有表达式解析
        Assertions.assertThrows(UnsatisfiedDependencyException.class, () -> defaultListableBeanFactory.getBean(FooManagerImpl.class).getFoo());
    }


    /**
     * 依赖注入
     * 没有表达式解析
     * 报错
     */
    @Test
    public void testWithQualifierAnnotationAutowireCandidateResolver() {
        defaultListableBeanFactory.setAutowireCandidateResolver(new QualifierAnnotationAutowireCandidateResolver());

        defaultListableBeanFactory.registerBeanDefinition("fooManagerImpl", new AnnotatedGenericBeanDefinition(FooManagerImpl.class));
        // 表达式解析
        Assertions.assertEquals("#{'${foo}'}", defaultListableBeanFactory.getBean(FooManagerImpl.class).getFoo());
    }
}
