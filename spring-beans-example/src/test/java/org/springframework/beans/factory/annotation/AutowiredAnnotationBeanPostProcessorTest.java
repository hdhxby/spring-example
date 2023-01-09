package org.springframework.beans.factory.annotation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import x.y.z.manager.FooManager;
import x.y.z.manager.impl.FooManagerImpl;
import x.y.z.service.FooService;
import x.y.z.service.impl.FooServiceImpl;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 实现依赖注入的方法
 */
public class AutowiredAnnotationBeanPostProcessorTest {

    private DefaultListableBeanFactory beanFactory;
    @BeforeEach
    public void setup() {
        beanFactory = new DefaultListableBeanFactory();
        beanFactory.setAutowireCandidateResolver(new QualifierAnnotationAutowireCandidateResolver()); // 没有表达式就不需要这句,不然会报错

        System.setProperty("foo","foo");
    }


    /**
     * 测试AutowiredAnnotationBeanPostProcessor
     * 自动依赖注入
     * @throws IOException
     */
    @Test
    public void testWithOutAutowiredAnnotationBeanPostProcessor() throws IOException {
        beanFactory.registerBeanDefinition("fooManagerImpl",new AnnotatedGenericBeanDefinition(FooManagerImpl.class));
        beanFactory.registerBeanDefinition("fooServiceImpl",new AnnotatedGenericBeanDefinition(FooServiceImpl.class));
        assertNotNull(beanFactory.getBean("fooManagerImpl"));
        assertInstanceOf(FooManager.class, beanFactory.getBean("fooManagerImpl"));
        assertNotNull(beanFactory.getBean("fooServiceImpl"));
        assertInstanceOf(FooService.class, beanFactory.getBean("fooServiceImpl"));
        // 依赖注入
        assertNull(beanFactory.getBean("fooServiceImpl",FooServiceImpl.class).getFooManager());
    }

    /**
     * 测试AutowiredAnnotationBeanPostProcessor
     * 自动依赖注入
     * @throws IOException
     */
    @Test
    public void testWithAutowiredAnnotationBeanPostProcessor() throws IOException {
        AutowiredAnnotationBeanPostProcessor autowiredAnnotationBeanPostProcessor = new AutowiredAnnotationBeanPostProcessor();
        autowiredAnnotationBeanPostProcessor.setBeanFactory(beanFactory);
        beanFactory.addBeanPostProcessor(autowiredAnnotationBeanPostProcessor);

        beanFactory.registerBeanDefinition("fooManagerImpl",new AnnotatedGenericBeanDefinition(FooManagerImpl.class));
        beanFactory.registerBeanDefinition("fooServiceImpl",new AnnotatedGenericBeanDefinition(FooServiceImpl.class));
        assertNotNull(beanFactory.getBean("fooManagerImpl"));
        assertInstanceOf(FooManager.class, beanFactory.getBean("fooManagerImpl"));
        assertNotNull(beanFactory.getBean("fooServiceImpl"));
        assertInstanceOf(FooService.class, beanFactory.getBean("fooServiceImpl"));
        // 依赖注入
        assertNotNull(beanFactory.getBean("fooServiceImpl",FooServiceImpl.class).getFooManager());
    }
}

