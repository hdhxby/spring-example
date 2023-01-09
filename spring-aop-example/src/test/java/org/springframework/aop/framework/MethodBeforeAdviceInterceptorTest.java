package org.springframework.aop.framework;

import org.aopalliance.intercept.MethodInterceptorTest;
import org.aopalliance.intercept.Joinpoint;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.framework.adapter.MethodBeforeAdviceInterceptor;
import x.y.z.manager.FooManager;
import x.y.z.manager.impl.FooManagerImpl;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class MethodBeforeAdviceInterceptorTest {

    private static final Logger logger = LoggerFactory.getLogger(MethodBeforeAdviceInterceptorTest.class);

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

        MethodBeforeAdviceInterceptor methodBeforeAdviceInterceptor = new MethodBeforeAdviceInterceptor(advice);

        Joinpoint joinPoint = new MethodInvocation() {

            @Override
            public Object proceed() throws Throwable{
//                return ((Method)getStaticPart()).invoke(getThis(), getArguments());
                return getMethod().invoke(getThis(), getArguments());
            }

            @Override
            public Object getThis() {
                return fooManager;
            }

            @Override
            public AccessibleObject getStaticPart() {
                return getMethod();
            }

            @Override
            public Object[] getArguments() {
                return new Object[0];
            }

            @Override
            public Method getMethod() {
                try {
                    return FooManagerImpl.class.getMethod("count");
                } catch (NoSuchMethodException e) {
                    return null;
                }
            }
        };
        // 手动执行
        assertEquals(1l, methodBeforeAdviceInterceptor.invoke((MethodInvocation)joinPoint));
        // 动态代理
        FooManager proxy = (FooManager) Proxy.newProxyInstance(MethodInterceptorTest.class.getClassLoader(), new Class[]{FooManager.class}, new InvocationHandler(){
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return methodBeforeAdviceInterceptor.invoke((MethodInvocation)joinPoint);
            }
        });

        assertEquals(1l, proxy.count());
    }

}
