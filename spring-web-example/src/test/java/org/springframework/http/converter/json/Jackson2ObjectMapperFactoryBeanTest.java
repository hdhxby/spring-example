package org.springframework.http.converter.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.mock.http.MockHttpInputMessage;
import x.y.z.bean.Foo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Jackson2ObjectMapperFactoryBeanTest {
    @Test
    public void test() throws IOException {
        Jackson2ObjectMapperFactoryBean jackson2ObjectMapperFactoryBean = new Jackson2ObjectMapperFactoryBean();
        jackson2ObjectMapperFactoryBean.setApplicationContext(new AnnotationConfigApplicationContext());
        ObjectMapper objectMapper = jackson2ObjectMapperFactoryBean.getObject();
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();

        Foo foo = (Foo) mappingJackson2HttpMessageConverter.read(Foo.class,new MockHttpInputMessage("{\"name\":\"name\"}".getBytes(StandardCharsets.UTF_8)));
        System.out.println(foo.getName());

    }
}
