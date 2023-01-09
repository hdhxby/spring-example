package org.springframework.aop.framework.autoproxy;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.Advisor;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.framework.adapter.MethodBeforeAdviceInterceptor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.SmartInstantiationAwareBeanPostProcessor;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import x.y.z.manager.FooManager;
import x.y.z.manager.impl.FooManagerImpl;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

/**
 * 跳过基础设施,其他对象创建代理
 */
public class InfrastructureAdvisorAutoProxyCreatorTest {

    private static final Logger logger = LoggerFactory.getLogger(InfrastructureAdvisorAutoProxyCreatorTest.class);

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
                        .setRole(BeanDefinition.ROLE_INFRASTRUCTURE) // 和DefaultAdvisorAutoProxyCreator不同的地方
                        .addConstructorArgValue(advice)
                .getBeanDefinition());

        InfrastructureAdvisorAutoProxyCreator infrastructureAdvisorAutoProxyCreator = new InfrastructureAdvisorAutoProxyCreator();
        assertInstanceOf(SmartInstantiationAwareBeanPostProcessor.class, infrastructureAdvisorAutoProxyCreator);
        // 会调用initBeanFactory方法创建BeanFactoryAdvisorRetrievalHelperAdapter,
        // BeanFactoryAdvisorRetrievalHelperAdapter负责从DefaultListableBeanFactory中收集Advisor
        infrastructureAdvisorAutoProxyCreator.setBeanFactory(defaultListableBeanFactory);
        // InfrastructureAdvisorAutoProxyCreator的isEligibleAdvisorBean认为@Role(BeanDefinition.ROLE_INFRASTRUCTURE)的是合法的AdvisorBean
        Object fooManager = infrastructureAdvisorAutoProxyCreator.wrapIfNecessary(new FooManagerImpl(),null,"fooManager");
        assertInstanceOf(FooManager.class, fooManager);
        ((FooManager)fooManager).count();
    }


}
