package org.springframework.core.type.filter;

import org.junit.jupiter.api.Test;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.SimpleMetadataReaderFactory;
import x.y.z.bean.Foo;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SimpleMetadataReaderFactoryTest {


    /**
     * 支持基于注解过滤
     * @throws IOException
     */
    @Test
    public void testAnnotationTypeFilter() throws IOException {
        SimpleMetadataReaderFactory simpleMetadataReaderFactory = new SimpleMetadataReaderFactory();
        MetadataReader simpleMetadataReader = simpleMetadataReaderFactory.getMetadataReader(Foo.class.getName());
        AnnotationTypeFilter annotationTypeFilter = new AnnotationTypeFilter(x.y.z.annotation.Foo.class);
        assertTrue(annotationTypeFilter.match(simpleMetadataReader,simpleMetadataReaderFactory));
    }
}

