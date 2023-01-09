package org.springframework.beans;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.core.type.classreading.SimpleMetadataReaderFactory;
import x.y.z.manager.FooManager;
import x.y.z.manager.impl.FooManagerImpl;
import x.y.z.service.FooService;
import x.y.z.service.impl.FooServiceImpl;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 已过时,可以通过这种编程的方式完成依赖注入
 */
@Deprecated
public class PropertyValuesTest {

    /**
     * 通过编程的方式可以实现依赖注入
     * @throws IOException
     */
    @Test
    public void testPropertyValue() throws IOException {
        SimpleMetadataReaderFactory simpleMetadataReaderFactory = new SimpleMetadataReaderFactory();
        AnnotatedGenericBeanDefinition fooManagerBeanDefinition = new AnnotatedGenericBeanDefinition(simpleMetadataReaderFactory
                .getMetadataReader(FooManagerImpl.class.getName()).getAnnotationMetadata());
        fooManagerBeanDefinition
                .getPropertyValues()
                .addPropertyValue(new PropertyValue("foo", FooManagerImpl.FOO));

        AnnotatedGenericBeanDefinition fooServiceBeanDefinition = new AnnotatedGenericBeanDefinition(simpleMetadataReaderFactory
                .getMetadataReader(FooServiceImpl.class.getName()).getAnnotationMetadata());
        fooServiceBeanDefinition
                .getPropertyValues()
                .addPropertyValue(new PropertyValue("fooManager",new RuntimeBeanReference(FooManagerImpl.class)));

        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.registerBeanDefinition("fooManagerImpl",fooManagerBeanDefinition);
        beanFactory.registerBeanDefinition("fooServiceImpl",fooServiceBeanDefinition);
        assertNotNull(beanFactory.getBean("fooManagerImpl"));
        assertInstanceOf(FooManager.class, beanFactory.getBean("fooManagerImpl"));
        assertNotNull(beanFactory.getBean("fooServiceImpl"));
        assertInstanceOf(FooService.class, beanFactory.getBean("fooServiceImpl"));
        // 依赖注入
        assertNotNull(beanFactory.getBean("fooServiceImpl",FooServiceImpl.class).getFooManager());
        // 依赖注入
        assertNotNull(beanFactory.getBean("fooManagerImpl",FooManagerImpl.class).getFoo());
    }
}

