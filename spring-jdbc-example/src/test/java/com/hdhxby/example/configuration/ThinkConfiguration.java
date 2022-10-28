package com.hdhxby.example.configuration;

import com.hdhxby.example.aop.ThinkAspect;
import com.hdhxby.example.entity.Heart;
import com.hdhxby.example.factory.config.ImportBeanPostProcessor;
import org.springframework.context.annotation.*;

//@ThinkRepositoryScan(value = "com.hdhxby.eshop")
//@EnableThinkRepository
@Import(ImportBeanPostProcessor.class)
@ComponentScans({
        @ComponentScan("com.hdhxby.eshop.bean"),
        @ComponentScan("com.hdhxby.eshop.entity")})
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
