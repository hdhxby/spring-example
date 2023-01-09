package org.springframework.aop.framework;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.framework.adapter.DefaultAdvisorAdapterRegistry;
import org.springframework.aop.framework.adapter.GlobalAdvisorAdapterRegistry;
import org.springframework.aop.framework.adapter.MethodBeforeAdviceInterceptor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import x.y.z.manager.FooManager;
import x.y.z.manager.impl.FooManagerImpl;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProxyFactoryTest {

    private static final Logger logger = LoggerFactory.getLogger(JdkDynamicAopProxyTest.class);

    /**
     * JdkDynamicAopProxy的上层接口
     * @throws NoSuchMethodException
     */
    @Test
    public void testMethodBeforeAdviceInterceptor() throws NoSuchMethodException {

        MethodBeforeAdviceInterceptor advice = new MethodBeforeAdviceInterceptor(new MethodBeforeAdvice() {
            @Override
            public void before(Method method, Object[] args, Object target) throws Throwable {
                logger.debug("方法调用前执行");
            }
        });

        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setInterfaces(FooManager.class);
        proxyFactory.setTargetClass(FooManagerImpl.class);
        proxyFactory.setTarget(new FooManagerImpl());

        proxyFactory.addAdvice(advice);
        assertEquals(1l,((FooManager)proxyFactory.getProxy()).count());
    }

    /**
     * JdkDynamicAopProxy的上层接口
     * @throws NoSuchMethodException
     */
    @Test
    public void testDefaultPointcutAdvisor() throws NoSuchMethodException {

        MethodBeforeAdviceInterceptor advice = new MethodBeforeAdviceInterceptor(new MethodBeforeAdvice() {
            @Override
            public void before(Method method, Object[] args, Object target) throws Throwable {
                logger.debug("方法调用前执行");
            }
        });

        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setInterfaces(FooManager.class);
        proxyFactory.setTargetClass(FooManagerImpl.class);
        proxyFactory.setTarget(new FooManagerImpl());

        proxyFactory.addAdvisor(new DefaultPointcutAdvisor(advice));
        assertEquals(1l,((FooManager)proxyFactory.getProxy()).count());
    }

    /**
     * JdkDynamicAopProxy的上层接口
     * @throws NoSuchMethodException
     */
    @Test
    public void testDefaultAdvisorAdapterRegistry() throws NoSuchMethodException {

        MethodBeforeAdviceInterceptor advice = new MethodBeforeAdviceInterceptor(new MethodBeforeAdvice() {
            @Override
            public void before(Method method, Object[] args, Object target) throws Throwable {
                logger.debug("方法调用前执行");
            }
        });

        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setInterfaces(FooManager.class);
        proxyFactory.setTargetClass(FooManagerImpl.class);
        proxyFactory.setTarget(new FooManagerImpl());

        proxyFactory.addAdvisor(new DefaultAdvisorAdapterRegistry().wrap(advice));
        assertEquals(1l,((FooManager)proxyFactory.getProxy()).count());
    }


    /**
     * JdkDynamicAopProxy的上层接口
     * @throws NoSuchMethodException
     */
    @Test
    public void testGlobalAdvisorAdapterRegistry() throws NoSuchMethodException {

        MethodBeforeAdviceInterceptor advice = new MethodBeforeAdviceInterceptor(new MethodBeforeAdvice() {
            @Override
            public void before(Method method, Object[] args, Object target) throws Throwable {
                logger.debug("方法调用前执行");
            }
        });

        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setInterfaces(FooManager.class);
        proxyFactory.setTargetClass(FooManagerImpl.class);
        proxyFactory.setTarget(new FooManagerImpl());

        proxyFactory.addAdvisor(GlobalAdvisorAdapterRegistry.getInstance().wrap(advice));
        assertEquals(1l,((FooManager)proxyFactory.getProxy()).count());
    }
}
