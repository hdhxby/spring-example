package org.springframework.http.converter.json;

import org.junit.jupiter.api.Test;
import org.springframework.mock.http.MockHttpInputMessage;
import x.y.z.bean.Foo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class MappingJackson2HttpMessageConverterTest {
    @Test
    public void test() throws IOException {
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();

        Foo foo = (Foo) mappingJackson2HttpMessageConverter.read(Foo.class,new MockHttpInputMessage("{\"name\":\"name\"}".getBytes(StandardCharsets.UTF_8)));
        System.out.println(foo.getName());
    }
}
