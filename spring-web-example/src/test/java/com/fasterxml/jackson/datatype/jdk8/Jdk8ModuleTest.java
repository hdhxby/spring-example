package com.fasterxml.jackson.datatype.jdk8;

import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Optional;

public class Jdk8ModuleTest {

    @Test
    public void test() throws IOException {
        JsonMapper jsonMapper = JsonMapper.builder()
                .build();

        jsonMapper.writeValue(System.out, Optional.empty());
    }

    @Test
    public void test2() throws IOException {
        JsonMapper jsonMapper = JsonMapper.builder()
                .addModule(new Jdk8Module())
                .build();

        jsonMapper.writeValue(System.out, Optional.empty());
    }
}
