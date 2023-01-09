package org.springframework.aop.framework;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.*;
import org.springframework.aop.framework.adapter.DefaultAdvisorAdapterRegistry;
import org.springframework.aop.framework.adapter.GlobalAdvisorAdapterRegistry;
import org.springframework.aop.framework.adapter.MethodBeforeAdviceInterceptor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import x.y.z.manager.FooManager;
import x.y.z.manager.impl.FooManagerImpl;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;


public class JdkDynamicAopProxyTest {

    private static final Logger logger = LoggerFactory.getLogger(JdkDynamicAopProxyTest.class);

    @Test
    public void testMethodBeforeAdviceInterceptor(){
        MethodBeforeAdviceInterceptor advice = new MethodBeforeAdviceInterceptor(new MethodBeforeAdvice() {
            @Override
            public void before(Method method, Object[] args, Object target) throws Throwable {
                logger.debug("方法调用前执行");
            }
        });

        AdvisedSupport advisedSupport = new AdvisedSupport();
        advisedSupport.setInterfaces(FooManager.class);
        advisedSupport.setTargetClass(FooManagerImpl.class);
        advisedSupport.setTarget(new FooManagerImpl());

        advisedSupport.addAdvice(advice);
        JdkDynamicAopProxy jdkDynamicAopProxy= new JdkDynamicAopProxy(advisedSupport);
        assertEquals(1l,((FooManager)jdkDynamicAopProxy.getProxy()).count());
    }


    @Test
    public void testDefaultPointcutAdvisor(){
        MethodBeforeAdviceInterceptor advice = new MethodBeforeAdviceInterceptor(new MethodBeforeAdvice() {
            @Override
            public void before(Method method, Object[] args, Object target) throws Throwable {
                logger.debug("方法调用前执行");
            }
        });

        AdvisedSupport advisedSupport = new AdvisedSupport();
        advisedSupport.setInterfaces(FooManager.class);
        advisedSupport.setTargetClass(FooManagerImpl.class);
        advisedSupport.setTarget(new FooManagerImpl());

        advisedSupport.addAdvisor(new DefaultPointcutAdvisor(advice));
        JdkDynamicAopProxy jdkDynamicAopProxy= new JdkDynamicAopProxy(advisedSupport);
        assertEquals(1l,((FooManager)jdkDynamicAopProxy.getProxy()).count());
    }


    @Test
    public void testJdkDynamicAopProxy(){
        MethodBeforeAdviceInterceptor advice = new MethodBeforeAdviceInterceptor(new MethodBeforeAdvice() {
            @Override
            public void before(Method method, Object[] args, Object target) throws Throwable {
                logger.debug("方法调用前执行");
            }
        });

        AdvisedSupport advisedSupport = new AdvisedSupport();
        advisedSupport.setInterfaces(FooManager.class);
        advisedSupport.setTargetClass(FooManagerImpl.class);
        advisedSupport.setTarget(new FooManagerImpl());

        advisedSupport.addAdvisor(new DefaultAdvisorAdapterRegistry().wrap(advice));
        JdkDynamicAopProxy jdkDynamicAopProxy= new JdkDynamicAopProxy(advisedSupport);
        assertEquals(1l,((FooManager)jdkDynamicAopProxy.getProxy()).count());
    }



    @Test
    public void testGlobalAdvisorAdapterRegistry(){
        MethodBeforeAdviceInterceptor advice = new MethodBeforeAdviceInterceptor(new MethodBeforeAdvice() {
            @Override
            public void before(Method method, Object[] args, Object target) throws Throwable {
                logger.debug("方法调用前执行");
            }
        });

        AdvisedSupport advisedSupport = new AdvisedSupport();
        advisedSupport.setInterfaces(FooManager.class);
        advisedSupport.setTargetClass(FooManagerImpl.class);
        advisedSupport.setTarget(new FooManagerImpl());

        advisedSupport.addAdvisor(GlobalAdvisorAdapterRegistry.getInstance().wrap(advice));
        JdkDynamicAopProxy jdkDynamicAopProxy= new JdkDynamicAopProxy(advisedSupport);
        assertEquals(1l,((FooManager)jdkDynamicAopProxy.getProxy()).count());
    }
}
