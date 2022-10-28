package org.springframework.core.type.classreading;

import lombok.extern.slf4j.Slf4j;

import static org.junit.Assert.*;
import org.junit.Test;
import org.springframework.asm.ClassReader;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
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



    @Test
    public void testBeanDefinition() throws IOException {
        SimpleMetadataReaderFactory simpleMetadataReaderFactory = new SimpleMetadataReaderFactory();
        SimpleMetadataReader simpleMetadataReader = (SimpleMetadataReader)simpleMetadataReaderFactory.getMetadataReader(FooServiceImpl.class.getName());
        ScannedGenericBeanDefinition scannedGenericBeanDefinition = new ScannedGenericBeanDefinition(simpleMetadataReader);
        assertEquals(FooServiceImpl.class.getName(),scannedGenericBeanDefinition.getBeanClassName());
    }

    @Test
    public void testBeanFactory() throws IOException {
        SimpleMetadataReaderFactory simpleMetadataReaderFactory = new SimpleMetadataReaderFactory();
        SimpleMetadataReader simpleMetadataReader = (SimpleMetadataReader)simpleMetadataReaderFactory.getMetadataReader(FooServiceImpl.class.getName());
        ScannedGenericBeanDefinition scannedGenericBeanDefinition = new ScannedGenericBeanDefinition(simpleMetadataReader);
        assertEquals(FooServiceImpl.class.getName(),scannedGenericBeanDefinition.getBeanClassName());

        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.registerBeanDefinition("fooServiceImpl",scannedGenericBeanDefinition);
        beanFactory.registerBeanDefinition("fooRepositoryImpl",new ScannedGenericBeanDefinition(simpleMetadataReaderFactory.getMetadataReader(FooRepositoryImpl.class.getName())));
        assertNotNull(beanFactory.getBean("fooServiceImpl"));
        assertTrue(beanFactory.getBean("fooServiceImpl") instanceof FooService);
        assertNotNull(beanFactory.getBean("fooRepositoryImpl"));
        assertTrue(beanFactory.getBean("fooRepositoryImpl") instanceof FooRepository);
        assertNull(beanFactory.getBean("fooServiceImpl",FooServiceImpl.class).getFooRepository());
    }

    @Test
    public void testPropertyValue() throws IOException {
        SimpleMetadataReaderFactory simpleMetadataReaderFactory = new SimpleMetadataReaderFactory();
        SimpleMetadataReader simpleMetadataReader = (SimpleMetadataReader)simpleMetadataReaderFactory.getMetadataReader(FooServiceImpl.class.getName());
        ScannedGenericBeanDefinition scannedGenericBeanDefinition = new ScannedGenericBeanDefinition(simpleMetadataReader);
        assertEquals(FooServiceImpl.class.getName(),scannedGenericBeanDefinition.getBeanClassName());

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
        assertNotNull(beanFactory.getBean("fooServiceImpl",FooServiceImpl.class).getFooRepository());
    }

    @Test
    public void testClassPathBeanDefinitionScanner(){
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        ClassPathBeanDefinitionScanner classPathBeanDefinitionScanner = new ClassPathBeanDefinitionScanner(beanFactory);
        classPathBeanDefinitionScanner.scan("x.y.z");
        assertNotNull(beanFactory.getBean("fooServiceImpl"));
        assertTrue(beanFactory.getBean("fooServiceImpl") instanceof FooService);
        assertNotNull(beanFactory.getBean("fooRepositoryImpl"));
        assertTrue(beanFactory.getBean("fooRepositoryImpl") instanceof FooRepository);
        assertNull(beanFactory.getBean("fooServiceImpl",FooServiceImpl.class).getFooRepository());
    }

    @Test
    public void testBeanPostProcessor() throws IOException {
        SimpleMetadataReaderFactory simpleMetadataReaderFactory = new SimpleMetadataReaderFactory();
        SimpleMetadataReader simpleMetadataReader = (SimpleMetadataReader)simpleMetadataReaderFactory.getMetadataReader(FooServiceImpl.class.getName());
        ScannedGenericBeanDefinition scannedGenericBeanDefinition = new ScannedGenericBeanDefinition(simpleMetadataReader);
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
        assertNotNull(beanFactory.getBean("fooServiceImpl",FooServiceImpl.class).getFooRepository());
    }


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
        assertNotNull(beanFactory.getBean("fooServiceImpl",FooServiceImpl.class).getFooRepository());
    }

}

