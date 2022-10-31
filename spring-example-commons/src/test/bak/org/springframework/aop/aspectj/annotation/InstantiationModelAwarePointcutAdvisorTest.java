package org.springframework.aop.aspectj.annotation;

import io.github.hdhxby.example.aop.ThinkAspect;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.aspectj.InstantiationModelAwarePointcutAdvisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import x.y.z.service.FooService;
import x.y.z.service.FooServiceImpl;

public class InstantiationModelAwarePointcutAdvisorTest {

    @Test
    public void testAspect() throws NoSuchMethodException {
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.addInterface(FooService.class);
        proxyFactory.setTarget(new FooServiceImpl());
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.registerBeanDefinition("thinkAspect",new AnnotatedGenericBeanDefinition(ThinkAspect.class));

        BeanFactoryAspectInstanceFactory beanFactoryAspectInstanceFactory = new BeanFactoryAspectInstanceFactory(beanFactory,"thinkAspect",ThinkAspect.class);

        ReflectiveAspectJAdvisorFactory aspectJAdvisorFactory = new ReflectiveAspectJAdvisorFactory();
        aspectJAdvisorFactory.getAdvisors(beanFactoryAspectInstanceFactory);

        AbstractAspectJAdvisorFactory.AspectJAnnotation<?> aspectJAnnotation =
                AbstractAspectJAdvisorFactory.findAspectJAnnotationOnMethod(ThinkAspect.class.getMethod("before"));

        AspectJExpressionPointcut declaredPointcut = new AspectJExpressionPointcut(ThinkAspect.class, new String[0], new Class<?>[0]);
        declaredPointcut.setExpression(aspectJAnnotation.getPointcutExpression());

        InstantiationModelAwarePointcutAdvisor instantiationModelAwarePointcutAdvisor  = new InstantiationModelAwarePointcutAdvisorImpl(declaredPointcut,
                ThinkAspect.class.getMethod("before"),
                aspectJAdvisorFactory,
                beanFactoryAspectInstanceFactory,
                0,
                null);
        proxyFactory.addAdvisor(instantiationModelAwarePointcutAdvisor);
        Assert.assertEquals("foo",((FooService)proxyFactory.getProxy()).foo());
    }
}
