package org.springframework.aop.support;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.jupiter.api.Test;
import org.springframework.aop.Pointcut;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DefaultPointcutAdvisorTest {

    @Test
    public void test() throws NoSuchMethodException {
        DefaultPointcutAdvisor defaultPointcutAdvisor = new DefaultPointcutAdvisor();
        assertEquals(Pointcut.TRUE,defaultPointcutAdvisor.getPointcut());
        assertTrue(defaultPointcutAdvisor.getPointcut().getClassFilter().matches(Object.class));
        assertTrue(defaultPointcutAdvisor.getPointcut().getMethodMatcher().matches(Object.class.getMethod("toString"),Object.class));
        // DefaultPointcutAdvisor 并没有前置或者后置的概念
        defaultPointcutAdvisor.setAdvice(new MethodInterceptor() {
            @Override
            public Object invoke(MethodInvocation invocation) throws Throwable {
                // 但是可以修改返回值
                return invocation.getMethod().invoke(invocation.getThis(),invocation.getArguments());
            }
        });
    }
}
