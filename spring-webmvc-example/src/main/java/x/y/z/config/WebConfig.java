package x.y.z.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;

import java.util.Map;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    public void WebConfig(SimpleUrlHandlerMapping simpleUrlHandlerMapping){
//        SimpleUrlHandlerMapping simpleUrlHandlerMapping = new SimpleUrlHandlerMapping();
        simpleUrlHandlerMapping.setUrlMap(Map.of("/simpleUrl","rooResourceImpl"));
    }

}
