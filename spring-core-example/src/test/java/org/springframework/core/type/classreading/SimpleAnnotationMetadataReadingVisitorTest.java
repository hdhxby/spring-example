package org.springframework.core.type.classreading;

import org.junit.jupiter.api.Test;
import org.springframework.asm.ClassReader;
import x.y.z.bean.Foo;
import x.y.z.manager.impl.FooManagerImpl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SimpleAnnotationMetadataReadingVisitorTest {


    private static final int PARSING_OPTIONS = ClassReader.SKIP_DEBUG
            | ClassReader.SKIP_CODE | ClassReader.SKIP_FRAMES;


    /**
     * 类文件访问者
     * 读取一个文件
     * @throws IOException
     */
    @Test
    public void testClassVisitor() throws IOException {
        ClassLoader classLoader = SimpleMetadataReaderFactoryTest.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(String.format("%s.class", FooManagerImpl.class.getName().replaceAll("\\.", File.separator)));
        ClassReader classReader = new ClassReader(inputStream);
//        byte[] bytes = inputStream.readAllBytes();
//        classReader = new ClassReader(bytes);
        SimpleAnnotationMetadataReadingVisitor simpleAnnotationMetadataReadingVisitor = new SimpleAnnotationMetadataReadingVisitor(classLoader);
        classReader.accept(simpleAnnotationMetadataReadingVisitor,PARSING_OPTIONS);
        SimpleAnnotationMetadata simpleAnnotationMetadata = simpleAnnotationMetadataReadingVisitor.getMetadata();
        assertNotNull(simpleAnnotationMetadata);
        assertEquals(FooManagerImpl.class.getName(), simpleAnnotationMetadata.getClassName());
    }
}
