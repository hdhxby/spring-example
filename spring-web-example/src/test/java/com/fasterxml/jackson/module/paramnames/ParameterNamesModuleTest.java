package com.fasterxml.jackson.module.paramnames;

import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;

public class ParameterNamesModuleTest {

    @Test
    public void test() throws IOException {
        JsonMapper jsonMapper = JsonMapper.builder()
                .build();

        jsonMapper.writeValue(System.out, Date.from(Instant.now()));
        jsonMapper.writeValue(System.out, Instant.now());
    }


    @Test
    public void testParameterNamesModule() throws IOException {
        JsonMapper jsonMapper = JsonMapper.builder()
                .addModule(new ParameterNamesModule())
                .build();

        jsonMapper.writeValue(System.out, Date.from(Instant.now()));
        jsonMapper.writeValue(System.out, Instant.now());
    }
}
