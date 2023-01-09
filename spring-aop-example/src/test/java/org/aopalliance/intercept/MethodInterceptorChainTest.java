package org.aopalliance.intercept;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import x.y.z.manager.FooManager;
import x.y.z.manager.impl.FooManagerImpl;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 测试拦截器链
 */
public class MethodInterceptorChainTest {

    private static final Logger logger = LoggerFactory.getLogger(MethodInterceptorChainTest.class);


    @Test
    public void testMethodInterceptor() throws Throwable {

        FooManagerImpl fooManager = new FooManagerImpl();

        MethodInterceptor methodInterceptor = new MethodInterceptor(){
            @Override
            public Object invoke(MethodInvocation invocation) throws Throwable {
                // 3.过程写死
                logger.debug("方法执行前");
                return invocation.proceed();
            }
        };

        MethodInterceptor methodInterceptorChain = new MethodInterceptor(){
            @Override
            public Object invoke(MethodInvocation invocation) throws Throwable {
                // 调用下一个
                logger.debug("拦截器调用前");
                return methodInterceptor.invoke(invocation);
            }
        };

        Joinpoint joinPoint = new MethodInvocation() {

            @Override
            public Object proceed() throws Throwable{
                // 两种方法都可以
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

        // 动态代理
        FooManager proxy = (FooManager) Proxy.newProxyInstance(MethodInterceptorChainTest.class.getClassLoader(), new Class[]{FooManager.class}, new InvocationHandler(){
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return methodInterceptorChain.invoke((MethodInvocation)joinPoint);
            }
        });

        assertEquals(1l, proxy.count());
    }

}
