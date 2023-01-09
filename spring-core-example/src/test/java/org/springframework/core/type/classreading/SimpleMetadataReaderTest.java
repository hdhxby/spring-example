package org.springframework.core.type.classreading;

import org.junit.jupiter.api.Test;
import org.springframework.asm.ClassReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import x.y.z.bean.Foo;
import x.y.z.manager.impl.FooManagerImpl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

public class SimpleMetadataReaderTest {

    /**
     * 元数据读取
     * 读取文件源数据
     * @throws IOException
     */
    @Test
    public void testMetadataReade() throws IOException {
        ClassLoader classLoader = SimpleMetadataReaderTest.class.getClassLoader();
        ClassPathResource resource = new ClassPathResource(String.format("%s.class", Foo.class.getName().replaceAll("\\.", File.separator)));
        SimpleMetadataReader simpleMetadataReader = new SimpleMetadataReader(resource,classLoader);
        SimpleAnnotationMetadata simpleAnnotationMetadata = (SimpleAnnotationMetadata) simpleMetadataReader.getAnnotationMetadata();
        assertNotNull(simpleAnnotationMetadata);
        assertEquals(FooManagerImpl.class.getName(), simpleAnnotationMetadata.getClassName());
    }
}

