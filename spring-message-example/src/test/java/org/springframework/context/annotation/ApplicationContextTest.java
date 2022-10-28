package org.springframework.context.annotation;

import com.hdhxby.example.configuration.ThinkConfiguration;
import com.hdhxby.example.entity.Speech;
import org.junit.Test;
import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.parsing.FailFastProblemReporter;
import org.springframework.beans.factory.support.DefaultBeanNameGenerator;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.type.classreading.SimpleMetadataReaderFactory;
import org.springframework.util.ClassUtils;
import x.y.z.bean.Foo;
import x.y.z.config.FooConfiguration;
import x.y.z.repository.FooRepository;
import x.y.z.service.FooService;
import x.y.z.service.FooServiceImpl;

import java.io.IOException;
import java.util.Set;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ApplicationContextTest {


    @Test
    public void testApplicationContext(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(FooConfiguration.class);
        applicationContext.refresh();

        assertNotNull(applicationContext.getBean("fooServiceImpl"));
        assertTrue(applicationContext.getBean("fooServiceImpl") instanceof FooService);
        assertNotNull(applicationContext.getBean("fooRepositoryImpl"));
        assertTrue(applicationContext.getBean("fooRepositoryImpl") instanceof FooRepository);
//        assertNotNull(applicationContext.getBean("fooServiceImpl",FooServiceImpl.class).getFooRepository());
    }


    @Test
    public void testApplicationContext1(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(FooConfiguration.class);
        applicationContext.refresh();

        assertNotNull(applicationContext.getBean("fooServiceImpl"));
        assertTrue(applicationContext.getBean("fooServiceImpl") instanceof FooService);
        assertNotNull(applicationContext.getBean("fooRepositoryImpl"));
        assertTrue(applicationContext.getBean("fooRepositoryImpl") instanceof FooRepository);
        assertNotNull(((FooServiceImpl)applicationContext.getBean(FooService.class)).getFooRepository());
    }

    @Test
    public void testConfigurationClassParser(){

        AnnotatedGenericBeanDefinition annotatedGenericBeanDefinition = new AnnotatedGenericBeanDefinition(FooConfiguration.class);

        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.registerBeanDefinition("fooConfiguration", annotatedGenericBeanDefinition);

        ConfigurationClassParser parser = new ConfigurationClassParser(
                new SimpleMetadataReaderFactory(), new FailFastProblemReporter(), new StandardEnvironment(),
                new DefaultResourceLoader(), new DefaultBeanNameGenerator(), beanFactory);

        for(String bean: beanFactory.getBeanDefinitionNames()){
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(bean);
            BeanDefinitionHolder beanDefinitionHolder = new BeanDefinitionHolder(beanDefinition,bean);
            parser.parse(Set.of(beanDefinitionHolder));
            Set<ConfigurationClass> configurationClass = parser.getConfigurationClasses();
        }
        beanFactory.getBean(Foo.class);
    }


    @Test
    public void test22(){

        AnnotatedGenericBeanDefinition annotatedGenericBeanDefinition = new AnnotatedGenericBeanDefinition(FooConfiguration.class);

        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.registerBeanDefinition("fooConfiguration", annotatedGenericBeanDefinition);

        ConfigurationClassParser parser = new ConfigurationClassParser(
                new SimpleMetadataReaderFactory(), new FailFastProblemReporter(), new StandardEnvironment(),
                new DefaultResourceLoader(), new DefaultBeanNameGenerator(), beanFactory);

        for(String bean: beanFactory.getBeanDefinitionNames()){
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(bean);
            BeanDefinitionHolder beanDefinitionHolder = new BeanDefinitionHolder(beanDefinition,bean);
            parser.parse(Set.of(beanDefinitionHolder));
            Set<ConfigurationClass> configurationClass = parser.getConfigurationClasses();
        }
        FooService fooService = beanFactory.getBean(FooService.class);
        AnnotationAwareAspectJAutoProxyCreator annotationAwareAspectJAutoProxyCreator = new AnnotationAwareAspectJAutoProxyCreator();
        annotationAwareAspectJAutoProxyCreator.postProcessAfterInitialization(fooService,"fooService");
        fooService.foo();
    }


    @Test
    public void test24(){

        AnnotatedGenericBeanDefinition annotatedGenericBeanDefinition = new AnnotatedGenericBeanDefinition(FooConfiguration.class);
        annotatedGenericBeanDefinition.getMetadata().getAnnotationAttributes("packageName");

        Set<AnnotationAttributes> componentScans = AnnotationConfigUtils.attributesForRepeatable(
                annotatedGenericBeanDefinition.getMetadata(), ComponentScans.class, ComponentScan.class);

    }

    @Test
    public void test23(){

        AnnotatedGenericBeanDefinition annotatedGenericBeanDefinition = new AnnotatedGenericBeanDefinition(FooConfiguration.class);

        AnnotationAwareAspectJAutoProxyCreator annotationAwareAspectJAutoProxyCreator = new AnnotationAwareAspectJAutoProxyCreator();
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        beanFactory.registerBeanDefinition("fooConfiguration", annotatedGenericBeanDefinition);
        ConfigurationClassParser parser = new ConfigurationClassParser(
                new SimpleMetadataReaderFactory(), new FailFastProblemReporter(), new StandardEnvironment(),
                new DefaultResourceLoader(), new DefaultBeanNameGenerator(), beanFactory);
        beanFactory.addBeanPostProcessor(annotationAwareAspectJAutoProxyCreator);
        for(String bean: beanFactory.getBeanDefinitionNames()){
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(bean);
            BeanDefinitionHolder beanDefinitionHolder = new BeanDefinitionHolder(beanDefinition,bean);
            parser.parse(Set.of(beanDefinitionHolder));
            Set<ConfigurationClass> configurationClass = parser.getConfigurationClasses();
        }

        FooService fooService = beanFactory.getBean(FooService.class);
        fooService.foo();
    }


    @Test
    public void test2(){

        AnnotatedGenericBeanDefinition annotatedGenericBeanDefinition = new AnnotatedGenericBeanDefinition(FooConfiguration.class);

        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.registerBeanDefinition("fooConfiguration",annotatedGenericBeanDefinition);
        try {
            beanFactory.getBean(Foo.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ConfigurationClassPostProcessor configurationClassPostProcessor = new ConfigurationClassPostProcessor();
        configurationClassPostProcessor.postProcessBeanDefinitionRegistry(beanFactory);
        configurationClassPostProcessor.postProcessBeanFactory(beanFactory);
        beanFactory.getBean(Foo.class);
    }

    @Test
    public void test3(){
        AnnotatedGenericBeanDefinition annotatedGenericBeanDefinition = new AnnotatedGenericBeanDefinition(FooConfiguration.class);

        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.registerBeanDefinition("thinkConfiguration",annotatedGenericBeanDefinition);

        ConfigurationClass configurationClass = new ConfigurationClass(annotatedGenericBeanDefinition.getMetadata(),"thinkConfiguration");

        Set<AnnotationAttributes> componentScans = AnnotationConfigUtils.attributesForRepeatable(
                configurationClass.getMetadata(), ComponentScans.class, ComponentScan.class);
        for(AnnotationAttributes annotationAttributes : componentScans) {
            ClassPathBeanDefinitionScanner classPathBeanDefinitionScanner = new ClassPathBeanDefinitionScanner(applicationContext);
            int count = classPathBeanDefinitionScanner.scan(annotationAttributes.getStringArray("basePackages"));
        }
    }


    @Test
    public void test31() throws IOException {

        AnnotatedGenericBeanDefinition annotatedGenericBeanDefinition = new AnnotatedGenericBeanDefinition(FooConfiguration.class);

        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.registerBeanDefinition("thinkConfiguration",annotatedGenericBeanDefinition);

        ConfigurationClassParser configurationClassParser = new ConfigurationClassParser(new SimpleMetadataReaderFactory(),new FailFastProblemReporter(), new StandardEnvironment(), new DefaultResourceLoader(),new DefaultBeanNameGenerator(),applicationContext);
        configurationClassParser.parse(annotatedGenericBeanDefinition.getMetadata(), FooConfiguration.class.getName());
        configurationClassParser.getConfigurationClasses();

    }
}
