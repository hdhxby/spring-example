package org.springframework.aop.framework.autoproxy;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.Advisor;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.framework.adapter.MethodBeforeAdviceInterceptor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.config.SmartInstantiationAwareBeanPostProcessor;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import x.y.z.manager.FooManager;
import x.y.z.manager.impl.FooManagerImpl;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;


public class DefaultAdvisorAutoProxyCreatorTest {

    private static final Logger logger = LoggerFactory.getLogger(DefaultAdvisorAutoProxyCreatorTest.class);

    @Test
    public void testDefaultAdvisorAutoProxyCreator() throws NoSuchMethodException {

        MethodBeforeAdviceInterceptor advice = new MethodBeforeAdviceInterceptor(new MethodBeforeAdvice() {
            @Override
            public void before(Method method, Object[] args, Object target) throws Throwable {
                logger.info("方法调用前执行");
            }
        });

        // GlobalAdvisorAdapterRegistry会将MethodBeforeAdviceInterceptor包装成一个DefaultPointcutAdvisor
        Advisor defaultPointcutAdvisor = new DefaultPointcutAdvisor(advice);
//        Advisor advisor = GlobalAdvisorAdapterRegistry.getInstance().wrap(advice);

        DefaultListableBeanFactory defaultListableBeanFactory = new DefaultListableBeanFactory();
        // 注册一个DefaultPointcutAdvisor
        defaultListableBeanFactory.registerBeanDefinition("advisor", BeanDefinitionBuilder
                .genericBeanDefinition(DefaultPointcutAdvisor.class)
                .addConstructorArgValue(advice)
                .getBeanDefinition());

        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        assertInstanceOf(SmartInstantiationAwareBeanPostProcessor.class, defaultAdvisorAutoProxyCreator);
        // 会调用initBeanFactory方法创建BeanFactoryAdvisorRetrievalHelperAdapter,
        // BeanFactoryAdvisorRetrievalHelperAdapter负责从DefaultListableBeanFactory中收集Advisor
        defaultAdvisorAutoProxyCreator.setBeanFactory(defaultListableBeanFactory);
        // DefaultAdvisorAutoProxyCreator的isEligibleAdvisorBean默认返回true,认为所有的Advisor都是合法AdvisorBean
        Object fooManager = defaultAdvisorAutoProxyCreator.wrapIfNecessary(new FooManagerImpl(),null,"fooManager");
        assertInstanceOf(FooManager.class, fooManager);
        ((FooManager)fooManager).count();
    }
}
