package io.github.hdhxby.example.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.AfterAdvice;

public class ThinkAfterAdviceMethodInterceptor implements MethodInterceptor, AfterAdvice {
    private static final Logger log = LoggerFactory.getLogger(ThinkAfterAdviceMethodInterceptor.class);
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object result = invocation.proceed();
        log.info("ThinkAfterAdviceMethodInterceptor");
        return result;
    }
}
