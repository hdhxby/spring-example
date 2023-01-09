package org.springframework.core.type.classreading;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.asm.ClassReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.lang.UsesJava8;
import x.y.z.bean.Foo;
import x.y.z.manager.FooManager;
import x.y.z.manager.impl.FooManagerImpl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class SimpleMetadataReaderFactoryTest {

    /**
     * 元数据读取工厂
     * 与上面的元数据读取相同,就是一层包装
     * @throws IOException
     */
    @Test
    public void testMetadataReaderFactory() throws IOException {
        SimpleMetadataReaderFactory simpleMetadataReaderFactory = new SimpleMetadataReaderFactory();
        SimpleMetadataReader simpleMetadataReader = (SimpleMetadataReader)simpleMetadataReaderFactory.getMetadataReader(Foo.class.getName());
        SimpleAnnotationMetadata simpleAnnotationMetadata = (SimpleAnnotationMetadata) simpleMetadataReader.getAnnotationMetadata();
        assertNotNull(simpleAnnotationMetadata);
    }

}

