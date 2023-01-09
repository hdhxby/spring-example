package org.springframework.beans.factory.support;

import org.junit.jupiter.api.Test;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.annotation.QualifierAnnotationAutowireCandidateResolver;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.core.type.classreading.SimpleMetadataReaderFactory;
import x.y.z.manager.FooManager;
import x.y.z.manager.impl.FooManagerImpl;
import x.y.z.repository.FooRepository;
import x.y.z.repository.impl.FooRepositoryImpl;
import x.y.z.service.FooService;
import x.y.z.service.impl.FooServiceImpl;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class DefaultListableBeanFactoryTest {


    /**
     * 单纯的DefaultListableBeanFactory可以注册获取BeanDefinition,但是不会依赖注入
     * @throws IOException
     */
    @Test
    public void testDefaultListableBeanFactory() throws IOException {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        AnnotatedGenericBeanDefinition fooManagerBeanDefinition = new AnnotatedGenericBeanDefinition(FooManagerImpl.class);
        AnnotatedGenericBeanDefinition fooServiceBeanDefinition = new AnnotatedGenericBeanDefinition(FooServiceImpl.class);

        beanFactory.registerBeanDefinition("fooManagerImpl",fooManagerBeanDefinition);
        beanFactory.registerBeanDefinition("fooServiceImpl",fooServiceBeanDefinition);
        assertNotNull(beanFactory.getBean("fooManagerImpl"));
        assertInstanceOf(FooManager.class, beanFactory.getBean("fooManagerImpl"));
        assertNotNull(beanFactory.getBean("fooServiceImpl"));
        assertInstanceOf(FooService.class, beanFactory.getBean("fooServiceImpl"));
        // 不会依赖注入
        assertNull(beanFactory.getBean("fooServiceImpl",FooServiceImpl.class).getFooManager());
        // 不会依赖注入
        assertNull(beanFactory.getBean("fooManagerImpl",FooManagerImpl.class).getFoo());
    }
}

