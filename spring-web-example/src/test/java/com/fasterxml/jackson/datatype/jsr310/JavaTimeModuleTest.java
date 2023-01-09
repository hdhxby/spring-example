package com.fasterxml.jackson.datatype.jsr310;

import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;

public class JavaTimeModuleTest {

    @Test
    public void testDate() throws IOException {
        JsonMapper jsonMapper = JsonMapper.builder()
                .build();

        jsonMapper.writeValue(System.out, Date.from(Instant.now()));
        jsonMapper.writeValue(System.out, Instant.now());
    }


    @Test
    public void testJavaTimeModule() throws IOException {
        JsonMapper jsonMapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();

        jsonMapper.writeValue(System.out, Date.from(Instant.now()));
        jsonMapper.writeValue(System.out, Instant.now());
    }
}
