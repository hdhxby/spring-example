package org.springframework.http.converter.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.mock.http.MockHttpInputMessage;
import x.y.z.bean.Foo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Jackson2ObjectMapperBuilderTest {
    @Test
    public void test() throws IOException {
        ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json().build();

    }
}
