package org.springframework.http.converter.json;

import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

public class SpringHandlerInstantiatorTest {
    @Test
    public void test() throws IOException {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext();
        SpringHandlerInstantiator springHandlerInstantiator = new SpringHandlerInstantiator(new AnnotationConfigApplicationContext()
                .getAutowireCapableBeanFactory());

        JsonMapper jsonMapper = JsonMapper.builder()
                .handlerInstantiator(springHandlerInstantiator)
                .build();


    }
}
