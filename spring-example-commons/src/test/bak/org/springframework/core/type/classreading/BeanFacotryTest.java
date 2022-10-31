package org.springframework.core.type.classreading;

import lombok.extern.slf4j.Slf4j;

import static org.junit.Assert.*;
import org.junit.Test;
import org.springframework.asm.ClassReader;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import x.y.z.bean.Foo;
import x.y.z.config.FooConfiguration;
import x.y.z.repository.FooRepository;
import x.y.z.repository.FooRepositoryImpl;
import x.y.z.service.FooService;
import x.y.z.service.FooServiceImpl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
@Slf4j
public class BeanFacotryTest {

    private static final int PARSING_OPTIONS = ClassReader.SKIP_DEBUG
            | ClassReader.SKIP_CODE | ClassReader.SKIP_FRAMES;

    private static final String PACKAGE = "x.y.z";

    public void test(){
        StandardEnvironment standardEnvironment = new StandardEnvironment();
    }

    /**
     * 类文件访问者
     * @throws IOException
     */
    @Test
    public void testClassVisitor() throws IOException {
        ClassLoader classLoader = BeanFacotryTest.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(Foo.class.getName().replaceAll("\\.", File.separator));
        ClassReader classReader = new ClassReader(inputStream);
//        byte[] bytes = inputStream.readAllBytes();
//        classReader = new ClassReader(bytes);
        SimpleAnnotationMetadataReadingVisitor simpleAnnotationMetadataReadingVisitor = new SimpleAnnotationMetadataReadingVisitor(classLoader);
        classReader.accept(simpleAnnotationMetadataReadingVisitor,PARSING_OPTIONS);
        SimpleAnnotationMetadata simpleAnnotationMetadata = simpleAnnotationMetadataReadingVisitor.getMetadata();
        assertNotNull(simpleAnnotationMetadata);
    }

    /**
     * 元数据读取
     * @throws IOException
     */
    @Test
    public void testMetadataReade() throws IOException {
        ClassLoader classLoader = BeanFacotryTest.class.getClassLoader();
        ClassPathResource resource = new ClassPathResource(String.format("%s.class", Foo.class.getName().replaceAll("\\.", File.separator)));
        SimpleMetadataReader simpleMetadataReader = new SimpleMetadataReader(resource,classLoader);
        SimpleAnnotationMetadata simpleAnnotationMetadata = (SimpleAnnotationMetadata) simpleMetadataReader.getAnnotationMetadata();
        assertNotNull(simpleAnnotationMetadata);
    }


    @Test
    public void testMetadataReaderFactory() throws IOException {
        SimpleMetadataReaderFactory simpleMetadataReaderFactory = new SimpleMetadataReaderFactory();
        SimpleMetadataReader simpleMetadataReader = (SimpleMetadataReader)simpleMetadataReaderFactory.getMetadataReader(Foo.class.getName());
        SimpleAnnotationMetadata simpleAnnotationMetadata = (SimpleAnnotationMetadata) simpleMetadataReader.getAnnotationMetadata();
        assertNotNull(simpleAnnotationMetadata);
    }

    @Test
    public void testPathMatchingResourcePatternResolver() throws IOException {
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        assertTrue(antPathMatcher.match("x.y.z.**.*",FooServiceImpl.class.getName()));
        PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = pathMatchingResourcePatternResolver.getResources(String.format("{}x.y.z.**", ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX));
        assertTrue(resources.length > 0);
    }

    @Test
    public void testAnnotationTypeFilter() throws IOException {
        SimpleMetadataReaderFactory simpleMetadataReaderFactory = new SimpleMetadataReaderFactory();
        SimpleMetadataReader simpleMetadataReader = (SimpleMetadataReader)simpleMetadataReaderFactory.getMetadataReader(FooServiceImpl.class.getName());
        AnnotationTypeFilter annotationTypeFilter = new AnnotationTypeFilter(Component.class);
        assertTrue(annotationTypeFilter.match(simpleMetadataReader,simpleMetadataReaderFactory));
    }
}

