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

import static org.junit.jupiter.api.Assertions.*;


public class MethodInterceptorTest {

    private static final Logger logger = LoggerFactory.getLogger(MethodInterceptorTest.class);


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
        assertEquals(1l, methodInterceptor.invoke((MethodInvocation)joinPoint));
        // 动态代理
        FooManager proxy = (FooManager) Proxy.newProxyInstance(MethodInterceptorTest.class.getClassLoader(), new Class[]{FooManager.class}, new InvocationHandler(){
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return methodInterceptor.invoke((MethodInvocation)joinPoint);
            }
        });

        assertEquals(1l, proxy.count());
    }

}
