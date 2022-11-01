package io.github.hdhxby.example.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.BeforeAdvice;

public class ThinkBeforeAdviceMethodInterceptor implements MethodInterceptor, BeforeAdvice {
    private static final Logger log = LoggerFactory.getLogger(ThinkBeforeAdviceMethodInterceptor.class);
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        log.info("ThinkBeforeAdviceMethodInterceptor");
        return invocation.proceed();
    }
}
