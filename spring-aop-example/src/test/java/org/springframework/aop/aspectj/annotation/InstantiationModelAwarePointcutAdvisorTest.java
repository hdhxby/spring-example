package org.springframework.aop.aspectj.annotation;

import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.aspectj.InstantiationModelAwarePointcutAdvisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import x.y.z.aop.LoggingAspect;
import x.y.z.config.FooConfiguration;
import x.y.z.manager.FooManager;
import x.y.z.manager.FooManagerImpl;

import static org.junit.jupiter.api.Assertions.*;

public class InstantiationModelAwarePointcutAdvisorTest {

    @Test
    public void testAspect() throws NoSuchMethodException {
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.addInterface(FooManager.class);
        proxyFactory.setTarget(new FooManagerImpl());
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.registerBeanDefinition("loggingAspect",new AnnotatedGenericBeanDefinition(LoggingAspect.class));

        BeanFactoryAspectInstanceFactory beanFactoryAspectInstanceFactory = new BeanFactoryAspectInstanceFactory(beanFactory,"loggingAspect",LoggingAspect.class);

        ReflectiveAspectJAdvisorFactory aspectJAdvisorFactory = new ReflectiveAspectJAdvisorFactory();
        aspectJAdvisorFactory.getAdvisors(beanFactoryAspectInstanceFactory);

        AbstractAspectJAdvisorFactory.AspectJAnnotation<?> aspectJAnnotation =
                AbstractAspectJAdvisorFactory.findAspectJAnnotationOnMethod(LoggingAspect.class.getMethod("before"));

        AspectJExpressionPointcut declaredPointcut = new AspectJExpressionPointcut(LoggingAspect.class, new String[0], new Class<?>[0]);
        declaredPointcut.setExpression(aspectJAnnotation.getPointcutExpression());

        InstantiationModelAwarePointcutAdvisor instantiationModelAwarePointcutAdvisor  = new InstantiationModelAwarePointcutAdvisorImpl(declaredPointcut,
                LoggingAspect.class.getMethod("before"),
                aspectJAdvisorFactory,
                beanFactoryAspectInstanceFactory,
                0,
                null);
        proxyFactory.addAdvisor(instantiationModelAwarePointcutAdvisor);
        assertEquals("foo",((FooManager)proxyFactory.getProxy()).foo());
    }

    @Test
    public void testAnnotationAwareAspectJAutoProxyCreator(){
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(FooConfiguration.class);
        assertEquals("foo",applicationContext.getBean(FooManager.class).foo());
    }
}
