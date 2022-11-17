package org.springframework.beans;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import x.y.z.config.FooConfiguration;
import x.y.z.repository.FooRepository;
import x.y.z.service.FooService;
import x.y.z.service.FooServiceImpl;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class BeanFactoryTest {

    /**
     * 测试ClassPathBeanDefinitionScanner
     */
    @Test
    public void testClassPathBeanDefinitionScanner(){
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        ClassPathBeanDefinitionScanner classPathBeanDefinitionScanner = new ClassPathBeanDefinitionScanner(beanFactory);
        classPathBeanDefinitionScanner.scan("x.y.z");
        assertNotNull(beanFactory.getBean("fooServiceImpl"));
        assertTrue(beanFactory.getBean("fooServiceImpl") instanceof FooService);
        assertNotNull(beanFactory.getBean("fooRepositoryImpl"));
        assertTrue(beanFactory.getBean("fooRepositoryImpl") instanceof FooRepository);
        // 不会依赖注入
        assertNull(beanFactory.getBean("fooServiceImpl",FooServiceImpl.class).getFooRepository());
    }

    /**
     * 测试ConfigurationClassPostProcessor
     */
    @Test
    public void testConfigurationClassPostProcessor(){
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.registerBeanDefinition("fooConfiguration", new AnnotatedGenericBeanDefinition(FooConfiguration.class));

        AutowiredAnnotationBeanPostProcessor autowiredAnnotationBeanPostProcessor = new AutowiredAnnotationBeanPostProcessor();
        autowiredAnnotationBeanPostProcessor.setBeanFactory(beanFactory);

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

