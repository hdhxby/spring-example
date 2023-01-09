package org.springframework.aop.framework.autoproxy;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.Advisor;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.aop.aspectj.annotation.ReflectiveAspectJAdvisorFactory;
import org.springframework.aop.framework.adapter.MethodBeforeAdviceInterceptor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.config.SmartInstantiationAwareBeanPostProcessor;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import x.y.z.aop.LoggingAspect;
import x.y.z.manager.FooManager;
import x.y.z.manager.impl.FooManagerImpl;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;


public class AnnotationAwareAspectJAutoProxyCreatorTest {

    private static final Logger logger = LoggerFactory.getLogger(AnnotationAwareAspectJAutoProxyCreatorTest.class);

    @Test
    public void testDefaultAdvisorAutoProxyCreator() throws NoSuchMethodException {

        MethodBeforeAdviceInterceptor advice = new MethodBeforeAdviceInterceptor(new MethodBeforeAdvice() {
            @Override
            public void before(Method method, Object[] args, Object target) throws Throwable {
                logger.debug("方法调用前执行");
            }
        });

        DefaultListableBeanFactory defaultListableBeanFactory = new DefaultListableBeanFactory();
        // 注册一个DefaultPointcutAdvisor
        defaultListableBeanFactory.registerBeanDefinition("loggingAspect", BeanDefinitionBuilder
                .genericBeanDefinition(LoggingAspect.class)
                .getBeanDefinition());
        AnnotationAwareAspectJAutoProxyCreator annotationAwareAspectJAutoProxyCreator = new AnnotationAwareAspectJAutoProxyCreator();
        assertInstanceOf(SmartInstantiationAwareBeanPostProcessor.class, annotationAwareAspectJAutoProxyCreator);
//        annotationAwareAspectJAutoProxyCreator.setIncludePatterns(List.of("execution(* x.y.z.manager.*.*(..))"));
        // 可以不设置,下一句会自动创建
        annotationAwareAspectJAutoProxyCreator.setAspectJAdvisorFactory(new ReflectiveAspectJAdvisorFactory(defaultListableBeanFactory));
        // 会调用initBeanFactory方法
        //  创建ReflectiveAspectJAdvisorFactory,负责从DefaultListableBeanFactory中收集Advisor
        //  创建BeanFactoryAspectJAdvisorsBuilderAdapter,负责包装成Advisor
        annotationAwareAspectJAutoProxyCreator.setBeanFactory(defaultListableBeanFactory);

        Object fooManager = annotationAwareAspectJAutoProxyCreator.wrapIfNecessary(new FooManagerImpl(),null,"fooManager");
        assertInstanceOf(FooManager.class, fooManager);
        ((FooManager)fooManager).count();
    }
}
