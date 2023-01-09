package x.y.z;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import x.y.z.manager.FooManager;
import x.y.z.repository.FooRepository;
import x.y.z.service.FooService;


public class ApplicationContextTest {

    @Test
    public void testManager() {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(ComponentScansWithManager.class);
        FooManager fooManager = annotationConfigApplicationContext.getBean(FooManager.class);
        fooManager.count();
    }

    @ComponentScans({
            @ComponentScan("x.y.z.manager")})
    @Configuration
    static class ComponentScansWithManager {
        // 简单调用
    }

    @Test
    public void testAspect() {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(ComponentScansWithAspectJCofiguration.class);
        FooManager fooManager = annotationConfigApplicationContext.getBean(FooManager.class);
        fooManager.count();
    }

    @ComponentScans({
            @ComponentScan("x.y.z.config.aspect"),
            @ComponentScan("x.y.z.manager")})
    @Configuration
    static class ComponentScansWithAspectJCofiguration {
        // aop
    }


    @Test
    public void testRepository() {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(ComponentScansWithRepository.class);
        FooRepository fooManager = annotationConfigApplicationContext.getBean(FooRepository.class);
        fooManager.count();
    }

    @ComponentScans({
            @ComponentScan("x.y.z.config.jdbc"),
            @ComponentScan("x.y.z.repository")})
    @Configuration
    static class ComponentScansWithRepository {
        // jdbc
    }

    @Test
    public void testService() {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(ComponentScansWithService.class);
        FooService fooManager = annotationConfigApplicationContext.getBean(FooService.class);
        fooManager.count();
    }

    @ComponentScans({
            @ComponentScan("x.y.z.config.tx"),
            @ComponentScan("x.y.z.config.jdbc"),
            @ComponentScan("x.y.z.service"),
            @ComponentScan("x.y.z.repository")})
    @Configuration
    static class ComponentScansWithService {
        // Service上的事务生效
    }
}
