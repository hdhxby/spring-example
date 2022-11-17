package x.y.z.webmvc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import x.y.z.webmvc.controller.SimpleUrlHandlerController;

import java.util.Map;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Order必须<2
     * @param simpleUrlHandlerController
     * @return
     */
    @Bean
    public SimpleUrlHandlerMapping simpleUrlHandlerMapping(SimpleUrlHandlerController simpleUrlHandlerController){
        SimpleUrlHandlerMapping simpleUrlHandlerMapping = new SimpleUrlHandlerMapping(Map.of("/simpleUrl/foo",simpleUrlHandlerController));
        simpleUrlHandlerMapping.setOrder(1);
        return simpleUrlHandlerMapping;
    }

    @Bean
    public SimpleUrlHandlerController simpleUrlHandlerController(){
        return new SimpleUrlHandlerController();
    }
}
