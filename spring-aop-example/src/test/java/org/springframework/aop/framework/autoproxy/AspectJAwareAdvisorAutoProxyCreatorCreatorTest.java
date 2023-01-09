package org.springframework.aop.framework.autoproxy;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.Advisor;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.springframework.aop.aspectj.autoproxy.AspectJAwareAdvisorAutoProxyCreator;
import org.springframework.aop.framework.adapter.MethodBeforeAdviceInterceptor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.config.SmartInstantiationAwareBeanPostProcessor;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import x.y.z.manager.FooManager;
import x.y.z.manager.impl.FooManagerImpl;

import java.lang.reflect.Method;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;


public class AspectJAwareAdvisorAutoProxyCreatorCreatorTest {

    private static final Logger logger = LoggerFactory.getLogger(AspectJAwareAdvisorAutoProxyCreatorCreatorTest.class);

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
        defaultListableBeanFactory.registerBeanDefinition("advisor", BeanDefinitionBuilder
                .genericBeanDefinition(AspectJExpressionPointcutAdvisor.class)
                .addPropertyValue("expression","execution(* x.y.z.manager.*.*(..))")
                .addPropertyValue("advice",advice)
                .getBeanDefinition());

        AspectJAwareAdvisorAutoProxyCreator aspectJAwareAdvisorAutoProxyCreator = new AspectJAwareAdvisorAutoProxyCreator();
        assertInstanceOf(SmartInstantiationAwareBeanPostProcessor.class, aspectJAwareAdvisorAutoProxyCreator);
        // 会调用initBeanFactory方法创建BeanFactoryAdvisorRetrievalHelperAdapter,
        // BeanFactoryAdvisorRetrievalHelperAdapter负责从DefaultListableBeanFactory中收集Advisor
        aspectJAwareAdvisorAutoProxyCreator.setBeanFactory(defaultListableBeanFactory);
        // AspectJAwareAdvisorAutoProxyCreator的isEligibleAdvisorBean默认返回true,认为所有的Advisor都是合法AdvisorBean
        Object fooManager = aspectJAwareAdvisorAutoProxyCreator.wrapIfNecessary(new FooManagerImpl(),null,"fooManager");
        assertInstanceOf(FooManager.class, fooManager);
        ((FooManager)fooManager).count();
    }
}
