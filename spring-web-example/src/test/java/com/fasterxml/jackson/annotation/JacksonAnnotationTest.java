package com.fasterxml.jackson.annotation;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonFactoryBuilder;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import io.github.hdhxby.example.entity.Person;
import io.github.hdhxby.example.entity.PersonCodec;
import io.github.hdhxby.example.entity.Pet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.ServiceLoader;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JacksonAnnotationTest {

    private static final Logger logger = LoggerFactory.getLogger(JacksonAnnotationTest.class);

    private JsonMapper jsonMapper;

    @BeforeEach
    public void setup(){
        jsonMapper = JsonMapper
                .builder()
                .build();
    }

}
