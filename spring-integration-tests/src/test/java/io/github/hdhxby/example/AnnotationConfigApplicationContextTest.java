package io.github.hdhxby.example;

import io.github.hdhxby.example.entity.Person;
import io.github.hdhxby.example.entity.Speech;
import io.github.hdhxby.example.factory.config.ApiBeanDefinitionRegistryPostProcessor;
import io.github.hdhxby.example.factory.config.ApiBeanFactoryPostProcessor;
import io.github.hdhxby.example.configuration.ThinkConfiguration;
import io.github.hdhxby.example.aop.ThinkAfterAdviceMethodInterceptor;
import io.github.hdhxby.example.aop.ThinkAfterPointcutAdvisor;
import io.github.hdhxby.example.aop.ThinkBeforeAdviceMethodInterceptor;
import io.github.hdhxby.example.aop.ThinkBeforePointcutAdvisor;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.framework.adapter.GlobalAdvisorAdapterRegistry;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AnnotationConfigApplicationContextTest {

    /**
     * 手动添加BeanFactoryPostProcessor
     */
    @Test
    public void test(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 添加BeanFactoryPostProcessor
        applicationContext.addBeanFactoryPostProcessor(new ApiBeanFactoryPostProcessor());
        applicationContext.addBeanFactoryPostProcessor(new ApiBeanDefinitionRegistryPostProcessor());
        applicationContext.register(ThinkConfiguration.class);

        applicationContext.refresh();
        applicationContext.getBean(Speech.class).speech();
    }

    @Test
    public void testAdvice(){
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.addInterface(Speech.class);
        proxyFactory.setTarget(new Person());
        proxyFactory.addAdvice(new ThinkBeforeAdviceMethodInterceptor());
        proxyFactory.addAdvice(new ThinkAfterAdviceMethodInterceptor());
        ((Speech)proxyFactory.getProxy()).speech();
    }

    @Test
    public void testAdvisor(){
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.addInterface(Speech.class);
        proxyFactory.setTarget(new Person());
        proxyFactory.addAdvisor(new ThinkBeforePointcutAdvisor());
        proxyFactory.addAdvisor(new ThinkAfterPointcutAdvisor());
        ((Speech)proxyFactory.getProxy()).speech();
    }

    @Test
    public void testAdvisorAdapter(){
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.addInterface(Speech.class);
        proxyFactory.setTarget(new Person());

        proxyFactory.addAdvisor(GlobalAdvisorAdapterRegistry.getInstance().wrap(new ThinkBeforeAdviceMethodInterceptor()));
        proxyFactory.addAdvisor(GlobalAdvisorAdapterRegistry.getInstance().wrap(new ThinkAfterAdviceMethodInterceptor()));
        ((Speech)proxyFactory.getProxy()).speech();
    }

    /**
     * 手动添加BeanFactoryPostProcessor
     */
    @Test
    public void test1(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ThinkConfiguration.class);
        System.out.println(1);
    }
}
