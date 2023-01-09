package org.springframework.aop.framework;

import org.aopalliance.intercept.MethodInterceptorTest;
import org.aopalliance.intercept.MethodInterceptor;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.framework.adapter.MethodBeforeAdviceInterceptor;
import x.y.z.manager.FooManager;
import x.y.z.manager.impl.FooManagerImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ReflectiveMethodInvocationTest {

    private static final Logger logger = LoggerFactory.getLogger(ReflectiveMethodInvocationTest.class);

    @Test
    public void testMethodBeforeAdvice() throws Throwable {
        FooManagerImpl fooManager = new FooManagerImpl();

        MethodBeforeAdvice advice = new MethodBeforeAdvice() {
            @Override
            public void before(Method method, Object[] args, Object target) throws Throwable {
                // 3. 过程已被优化,Advice中实现,不需要硬编码到MethodInterceptor
                logger.debug("方法执行前");
            }
        };

        // 先调用MethodBeforeAdvice的before方法,后效用MethodInvocation
        MethodInterceptor methodInterceptor = new MethodBeforeAdviceInterceptor(advice);

        // 动态代理
        FooManager proxy = (FooManager) Proxy.newProxyInstance(MethodInterceptorTest.class.getClassLoader(), new Class[]{FooManager.class}, new InvocationHandler(){
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                ReflectiveMethodInvocation reflectiveMethodInvocation = new ReflectiveMethodInvocation(proxy,
                        fooManager,
                        method,
                        args,
                        proxy.getClass(),
                        List.of(methodInterceptor)
                );
                return reflectiveMethodInvocation.proceed();
            }
        });

        assertEquals(1l, proxy.count());
    }

}
