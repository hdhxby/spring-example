package org.springframework.beans;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.type.classreading.SimpleMetadataReaderFactory;
import x.y.z.config.FooConfiguration;
import x.y.z.repository.FooRepository;
import x.y.z.repository.FooRepositoryImpl;
import x.y.z.service.FooService;
import x.y.z.service.FooServiceImpl;

import java.io.IOException;

import static org.junit.Assert.*;

@Slf4j
public class BeanFacotryTest {


    /**
     * 通过AnnotatedGenericBeanDefinition
     * @throws IOException
     */
    @Test
    public void testAnnotatedGenericBeanDefinition() throws IOException {
        AnnotatedGenericBeanDefinition annotatedGenericBeanDefinition = new AnnotatedGenericBeanDefinition(FooServiceImpl.class);
        assertEquals(FooServiceImpl.class.getName(),annotatedGenericBeanDefinition.getBeanClassName());
    }

    /**
     * 通过SimpleMetadataReaderFactory获取BeanDefinition
     * @throws IOException
     */
    @Test
    public void testScannedGenericBeanDefinition() throws IOException {
        SimpleMetadataReaderFactory simpleMetadataReaderFactory = new SimpleMetadataReaderFactory();
        ScannedGenericBeanDefinition scannedGenericBeanDefinition = new ScannedGenericBeanDefinition(simpleMetadataReaderFactory
                .getMetadataReader(FooServiceImpl.class.getName()));
        assertEquals(FooServiceImpl.class.getName(),scannedGenericBeanDefinition.getBeanClassName());
    }

    /**
     * 手动创建DefaultListableBeanFactory
     * @throws IOException
     */
    @Test
    public void testBeanFactory() throws IOException {
        SimpleMetadataReaderFactory simpleMetadataReaderFactory = new SimpleMetadataReaderFactory();
        ScannedGenericBeanDefinition scannedGenericBeanDefinition = new ScannedGenericBeanDefinition(simpleMetadataReaderFactory
                .getMetadataReader(FooServiceImpl.class.getName()));

        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.registerBeanDefinition("fooServiceImpl",scannedGenericBeanDefinition);
        beanFactory.registerBeanDefinition("fooRepositoryImpl",new ScannedGenericBeanDefinition(simpleMetadataReaderFactory
                .getMetadataReader(FooRepositoryImpl.class.getName())));
        assertNotNull(beanFactory.getBean("fooServiceImpl"));
        assertTrue(beanFactory.getBean("fooServiceImpl") instanceof FooService);
        assertNotNull(beanFactory.getBean("fooRepositoryImpl"));
        assertTrue(beanFactory.getBean("fooRepositoryImpl") instanceof FooRepository);
        assertNull(beanFactory.getBean("fooServiceImpl",FooServiceImpl.class).getFooRepository());
    }

    /**
     * 测试PropertyValue
     * @throws IOException
     */
    @Test
    public void testPropertyValue() throws IOException {
        SimpleMetadataReaderFactory simpleMetadataReaderFactory = new SimpleMetadataReaderFactory();
        ScannedGenericBeanDefinition scannedGenericBeanDefinition = new ScannedGenericBeanDefinition(simpleMetadataReaderFactory
                .getMetadataReader(FooServiceImpl.class.getName()));

        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.registerBeanDefinition("fooServiceImpl",scannedGenericBeanDefinition);
        beanFactory.registerBeanDefinition("fooRepositoryImpl",new ScannedGenericBeanDefinition(simpleMetadataReaderFactory.getMetadataReader(FooRepositoryImpl.class.getName())));
        scannedGenericBeanDefinition
                .getPropertyValues()
//                .addPropertyValue(new FooRepositoryImpl());
//                .addPropertyValue(new PropertyValue("fooRepository",new RuntimeBeanReference("fooRepositoryImpl")));
                .addPropertyValue(new PropertyValue("fooRepository",new RuntimeBeanReference(FooRepositoryImpl.class)));
        assertNotNull(beanFactory.getBean("fooServiceImpl"));
        assertTrue(beanFactory.getBean("fooServiceImpl") instanceof FooService);
        assertNotNull(beanFactory.getBean("fooRepositoryImpl"));
        assertTrue(beanFactory.getBean("fooRepositoryImpl") instanceof FooRepository);
        // 不会依赖注入
        assertNotNull(beanFactory.getBean("fooServiceImpl",FooServiceImpl.class).getFooRepository());
    }

    /**
     * 测试AutowiredAnnotationBeanPostProcessor
     * @throws IOException
     */
    @Test
    public void testBeanPostProcessor() throws IOException {
        SimpleMetadataReaderFactory simpleMetadataReaderFactory = new SimpleMetadataReaderFactory();
        ScannedGenericBeanDefinition scannedGenericBeanDefinition = new ScannedGenericBeanDefinition(simpleMetadataReaderFactory
                .getMetadataReader(FooServiceImpl.class.getName()));
        assertEquals(FooServiceImpl.class.getName(),scannedGenericBeanDefinition.getBeanClassName());

        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        AutowiredAnnotationBeanPostProcessor autowiredAnnotationBeanPostProcessor = new AutowiredAnnotationBeanPostProcessor();
        autowiredAnnotationBeanPostProcessor.setBeanFactory(beanFactory);

        beanFactory.addBeanPostProcessor(autowiredAnnotationBeanPostProcessor);
        beanFactory.registerBeanDefinition("fooServiceImpl",scannedGenericBeanDefinition);
        beanFactory.registerBeanDefinition("fooRepositoryImpl",new ScannedGenericBeanDefinition(simpleMetadataReaderFactory.getMetadataReader(FooRepositoryImpl.class.getName())));
        assertNotNull(beanFactory.getBean("fooServiceImpl"));
        assertTrue(beanFactory.getBean("fooServiceImpl") instanceof FooService);
        assertNotNull(beanFactory.getBean("fooRepositoryImpl"));
        assertTrue(beanFactory.getBean("fooRepositoryImpl") instanceof FooRepository);
        // 依赖注入
        assertNotNull(beanFactory.getBean("fooServiceImpl",FooServiceImpl.class).getFooRepository());
    }
}

