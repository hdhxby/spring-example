package x.y.z.webmvc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.web.servlet.function.RequestPredicates.GET;
import static org.springframework.web.servlet.function.RequestPredicates.path;

@Configuration
public class RouteConfiguration {
    @Bean
    public RouterFunction<ServerResponse> routerFunction(){
        return RouterFunctions.nest(path("/route"),RouterFunctions.route(GET("/foo"),request -> {
           return ServerResponse.ok().body("foo");
        }));
    }
}
