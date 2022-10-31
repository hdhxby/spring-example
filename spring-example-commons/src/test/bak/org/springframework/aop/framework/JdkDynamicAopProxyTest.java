package org.springframework.aop.framework;

import org.aopalliance.intercept.Joinpoint;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.*;
import org.springframework.aop.framework.adapter.DefaultAdvisorAdapterRegistry;
import org.springframework.aop.framework.adapter.MethodBeforeAdviceInterceptor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import x.y.z.service.FooService;
import x.y.z.service.FooServiceImpl;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class JdkDynamicAopProxyTest {

    private static final Logger logger = LoggerFactory.getLogger(JdkDynamicAopProxyTest.class);


    @Test
    public void testAdvice() throws Throwable {

        FooServiceImpl fooService = new FooServiceImpl();

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
                return fooService;
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
                    return FooServiceImpl.class.getMethod("foo");
                } catch (NoSuchMethodException e) {
                    return null;
                }
            }
        };

        assertEquals("foo", methodInterceptor.invoke((MethodInvocation)joinPoint));

        FooService proxy = (FooService) Proxy.newProxyInstance(JdkDynamicAopProxyTest.class.getClassLoader(), new Class[]{FooService.class}, new InvocationHandler(){
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return methodInterceptor.invoke((MethodInvocation)joinPoint);
            }
        });

        assertEquals("foo", proxy.foo());
    }

    @Test
    public void testReflectiveMethodInvocation() throws Throwable {
        FooServiceImpl fooService = new FooServiceImpl();

        MethodInterceptor methodInterceptor = new MethodInterceptor(){
            @Override
            public Object invoke(MethodInvocation invocation) throws Throwable {
                // 3.过程写死
                logger.debug("方法执行前");
                return invocation.proceed();
            }
        };

        ReflectiveMethodInvocation reflectiveMethodInvocation = new ReflectiveMethodInvocation(null,
                fooService,
                FooServiceImpl.class.getMethod("foo"),
                new Object[0],
                FooService.class,
                List.of(methodInterceptor)
        );

        assertEquals("foo", methodInterceptor.invoke(reflectiveMethodInvocation));


        FooService proxy = (FooService) Proxy.newProxyInstance(JdkDynamicAopProxyTest.class.getClassLoader(), new Class[]{FooService.class}, new InvocationHandler(){
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return methodInterceptor.invoke(reflectiveMethodInvocation);
            }
        });

        assertEquals("foo", proxy.foo());
    }

    @Test
    public void testMethodBeforeAdvice() throws Throwable {
        FooServiceImpl fooService = new FooServiceImpl();


        MethodBeforeAdvice advice = new MethodBeforeAdvice() {
            @Override
            public void before(Method method, Object[] args, Object target) throws Throwable {
                // 3. 过程已被优化,Advice中实现,不需要硬编码到MethodInterceptor
                logger.debug("方法执行前");
            }
        };

        // 先调用MethodBeforeAdvice的before方法,后效用MethodInvocation
        MethodInterceptor methodInterceptor = new MethodBeforeAdviceInterceptor(advice);

        ReflectiveMethodInvocation reflectiveMethodInvocation = new ReflectiveMethodInvocation(null,
                fooService,
                FooServiceImpl.class.getMethod("foo"),
                new Object[0],
                FooService.class,
                List.of(methodInterceptor)
        );

        PointcutAdvisor advisor = new DefaultPointcutAdvisor(methodInterceptor);

        assertTrue(advisor.getPointcut().getClassFilter().matches(FooServiceImpl.class));
        assertTrue(advisor.getPointcut().getMethodMatcher().matches(FooServiceImpl.class.getMethod("foo"), FooServiceImpl.class));
//        if(advisor.getPointcut().getClassFilter().matches(FooServiceImpl.class)
//                && advisor.getPointcut().getMethodMatcher().matches(FooServiceImpl.class.getMethod("foo"), FooServiceImpl.class)) {
        assertEquals("foo", ((MethodBeforeAdviceInterceptor) advisor.getAdvice()).invoke(reflectiveMethodInvocation));
//        }

        FooService proxy = (FooService) Proxy.newProxyInstance(JdkDynamicAopProxyTest.class.getClassLoader(), new Class[]{FooService.class}, new InvocationHandler(){
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return methodInterceptor.invoke(reflectiveMethodInvocation);
            }
        });

        assertEquals("foo", proxy.foo());
    }


    @Test
    public void testDefaultPointcutAdvisor() throws Throwable {
        FooServiceImpl fooService = new FooServiceImpl();


        MethodInvocation methodInvocation = new MethodInvocation() {

            @Override
            public Object proceed() throws Throwable{
                return getMethod().invoke(getThis(), getArguments());
            }

            @Override
            public Object getThis() {
                // 1.对象写死
                return fooService;
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
                    // 2.类和方法写死
                    return FooServiceImpl.class.getMethod("foo");
                } catch (NoSuchMethodException e) {
                    return null;
                }
            }
        };

        MethodBeforeAdvice advice = new MethodBeforeAdvice() {
            @Override
            public void before(Method method, Object[] args, Object target) throws Throwable {
                // 3. 过程已被优化,Advice中实现,不需要硬编码到MethodInterceptor
                logger.debug("方法执行前");
            }
        };

        // 先调用MethodBeforeAdvice的before方法,后调用MethodInvocation
        MethodInterceptor methodInterceptor = new MethodBeforeAdviceInterceptor(advice);

        PointcutAdvisor advisor = new DefaultPointcutAdvisor(methodInterceptor);

//        Pointcut pointcut = advisor.getPointcut();
//        assertTrue(pointcut instanceof TruePointcut);
//        assertTrue(pointcut.getClassFilter() instanceof TrueClassFilter);
//        assertTrue(pointcut.getMethodMatcher() instanceof TrueMethodMatcher);

        assertTrue(advisor.getPointcut().getClassFilter().matches(FooServiceImpl.class));
        assertTrue(advisor.getPointcut().getMethodMatcher().matches(FooServiceImpl.class.getMethod("foo"), FooServiceImpl.class));
//        if(advisor.getPointcut().getClassFilter().matches(FooServiceImpl.class)
//                && advisor.getPointcut().getMethodMatcher().matches(FooServiceImpl.class.getMethod("foo"), FooServiceImpl.class)) {
        assertEquals("foo", ((MethodBeforeAdviceInterceptor) advisor.getAdvice()).invoke(methodInvocation));
//        }

        FooService proxy = (FooService) Proxy.newProxyInstance(JdkDynamicAopProxyTest.class.getClassLoader(), new Class[]{FooService.class}, new InvocationHandler(){
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return methodInterceptor.invoke(methodInvocation);
            }
        });

        assertEquals("foo", proxy.foo());
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
        advisedSupport.setInterfaces(FooService.class);
        advisedSupport.setTargetClass(FooServiceImpl.class);
//        advisedSupport.addAdvice(advice);
        advisedSupport.addAdvisor(new DefaultAdvisorAdapterRegistry().wrap(advice));
        JdkDynamicAopProxy jdkDynamicAopProxy= new JdkDynamicAopProxy(advisedSupport);
        advisedSupport.setTarget(new FooServiceImpl());
        Assert.assertEquals("foo",((FooService)jdkDynamicAopProxy.getProxy()).foo());
    }


    @Test
    public void testProxyFactory() throws NoSuchMethodException {

        MethodBeforeAdviceInterceptor advice = new MethodBeforeAdviceInterceptor(new MethodBeforeAdvice() {
            @Override
            public void before(Method method, Object[] args, Object target) throws Throwable {
                logger.debug("方法调用前执行");
            }
        });

        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setInterfaces(FooService.class);
        proxyFactory.setTargetClass(FooServiceImpl.class);
//        advisedSupport.addAdvice(advice);
        proxyFactory.addAdvisor(new DefaultAdvisorAdapterRegistry().wrap(advice));
        proxyFactory.setTarget(new FooServiceImpl());
        Assert.assertEquals("foo",((FooService)proxyFactory.getProxy()).foo());
    }
}
