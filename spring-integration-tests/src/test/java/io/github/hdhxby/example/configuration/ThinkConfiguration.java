package io.github.hdhxby.example.configuration;

import io.github.hdhxby.example.aop.ThinkAspect;
import io.github.hdhxby.example.entity.Heart;
import io.github.hdhxby.example.factory.config.ImportBeanPostProcessor;
import org.springframework.context.annotation.*;

//@ThinkRepositoryScan(value = "io.github.hdhxby.example")
//@EnableThinkRepository
@Import(ImportBeanPostProcessor.class)
@ComponentScans({
        @ComponentScan("io.github.hdhxby.example.bean")
//        , @ComponentScan("io.github.hdhxby.example.entity")
})
@EnableAspectJAutoProxy
@Configuration
public class ThinkConfiguration {

    @Bean
    public ThinkAspect thinkAspect(){
        return new ThinkAspect();
    }

    @Bean
    public Heart heart(){
        return new Heart();
    }
}
