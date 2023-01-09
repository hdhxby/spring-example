package x.y.z.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Role;

@Aspect
public class LoggingAspect {

    private Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    public static final String POINTCUT = "execution(* x.y.z.manager..*(..))";
    @Pointcut(POINTCUT)
    public void point(){};

    @Before("point()")
    public void before(){
        logger.debug("方法调用前");
    }

//    @Around("point()")
    public void around(ProceedingJoinPoint joinPoint){
        try{
            logger(joinPoint).info("方法调用前");
            joinPoint.proceed();
            logger(joinPoint).info("方法调用后");
        }  catch (Throwable throwable) {
            logger(joinPoint).error("方法异常", throwable);
        }
    };



//    @After("point()")
    public void after(){
        logger.debug("方法调用后");
    }

    private Logger logger(JoinPoint joinPoint) {
        return LoggerFactory.getLogger(joinPoint.getSignature().getDeclaringType());
    }
}
