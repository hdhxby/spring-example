package com.fasterxml.jackson.datatype;

import com.fasterxml.jackson.annotation.JacksonAnnotationTest;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.ServiceLoader;

public class ModuleTest {

    private static final Logger logger = LoggerFactory.getLogger(ModuleTest.class);

    @Test
    public void testJSR310Module() throws IOException {
        ServiceLoader.load(Module.class)
                .stream()
                .map(ServiceLoader.Provider::get)
                .forEach(module -> logger.debug("{}", module));
    }
}
