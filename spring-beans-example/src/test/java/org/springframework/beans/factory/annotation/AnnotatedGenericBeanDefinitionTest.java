package org.springframework.beans.factory.annotation;

import org.junit.jupiter.api.Test;
import org.springframework.core.type.classreading.SimpleMetadataReaderFactory;
import x.y.z.repository.impl.FooRepositoryImpl;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class AnnotatedGenericBeanDefinitionTest {


    /**
     * 通过AnnotatedGenericBeanDefinition
     * @throws IOException
     */
    @Test
    public void testAnnotatedGenericBeanDefinition() throws IOException {
        AnnotatedGenericBeanDefinition annotatedGenericBeanDefinition = new AnnotatedGenericBeanDefinition(FooRepositoryImpl.class);
        assertEquals(FooRepositoryImpl.class.getName(),annotatedGenericBeanDefinition.getBeanClassName());
    }

    /**
     * 通过SimpleMetadataReaderFactory获取BeanDefinition
     * @throws IOException
     */
    @Test
    public void testSimpleMetadataReaderFactory() throws IOException {
        SimpleMetadataReaderFactory simpleMetadataReaderFactory = new SimpleMetadataReaderFactory();
        AnnotatedGenericBeanDefinition AnnotatedGenericBeanDefinition = new AnnotatedGenericBeanDefinition(simpleMetadataReaderFactory
                .getMetadataReader(FooRepositoryImpl.class.getName())
                .getAnnotationMetadata());
        assertEquals(FooRepositoryImpl.class.getName(),AnnotatedGenericBeanDefinition.getBeanClassName());
    }
}

