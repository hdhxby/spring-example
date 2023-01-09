package com.fasterxml.jackson.datatype.jsr310;

import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;

public class JSR310ModuleTest {

    @Test
    public void testDate() throws IOException {
        JsonMapper jsonMapper = JsonMapper.builder()
                .build();

        jsonMapper.writeValue(System.out, Date.from(Instant.now()));
        jsonMapper.writeValue(System.out, Instant.now());
    }


    @Test
    public void testJSR310Module() throws IOException {
        JsonMapper jsonMapper = JsonMapper.builder()
                .addModule(new JSR310Module())
                .build();

        jsonMapper.writeValue(System.out, Date.from(Instant.now()));
        jsonMapper.writeValue(System.out, Instant.now());
    }


}
