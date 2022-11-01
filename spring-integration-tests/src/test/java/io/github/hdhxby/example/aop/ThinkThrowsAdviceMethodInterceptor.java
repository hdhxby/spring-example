package io.github.hdhxby.example.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.ThrowsAdvice;

public class ThinkThrowsAdviceMethodInterceptor implements MethodInterceptor, ThrowsAdvice {
    private static final Logger log = LoggerFactory.getLogger(ThinkThrowsAdviceMethodInterceptor.class);
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object result;
        try {
            return invocation.proceed();
        } catch (Exception e) {
            log.error("ThinkThrowsAdviceMethodInterceptor",e);
            throw e;
        }
    }
}
