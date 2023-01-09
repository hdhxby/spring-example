package org.springframework.aop.aspectj;

import org.junit.jupiter.api.Test;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.annotation.*;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.interceptor.ExposeInvocationInterceptor;
import x.y.z.aop.LoggingAspect;
import x.y.z.manager.FooManager;
import x.y.z.manager.impl.FooManagerImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReflectiveAspectJAdvisorFactoryTest {

    @Test
    public void test() {
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.addInterface(FooManager.class);
        proxyFactory.setTarget(new FooManagerImpl());

        MetadataAwareAspectInstanceFactory metadataAwareAspectInstanceFactory = new SimpleMetadataAwareAspectInstanceFactory(LoggingAspect.class,"loggingAspect");
        List<Advisor> advisors = new ReflectiveAspectJAdvisorFactory()
                .getAdvisors(metadataAwareAspectInstanceFactory);
        proxyFactory.addAdvisor(ExposeInvocationInterceptor.ADVISOR); // 设置ThreadLocal,没有这个会报错.
        proxyFactory.addAdvisors(advisors);
        assertEquals(1l,((FooManager)proxyFactory.getProxy()).count());
    }
}
