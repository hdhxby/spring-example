package x.y.z.config.aspect;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import x.y.z.aop.LoggingAspect;

@EnableAspectJAutoProxy
@Configuration
public class AspectJCofiguration {

    @Bean
    public LoggingAspect loggingAspect(){
        return new LoggingAspect();
    }
}
