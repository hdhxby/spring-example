package com.fasterxml.jackson.core;

import com.fasterxml.jackson.annotation.JacksonAnnotationTest;
import com.fasterxml.jackson.databind.json.JsonMapper;
import io.github.hdhxby.example.entity.Person;
import io.github.hdhxby.example.entity.PersonCodec;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ObjectCodecTest {

    private static final Logger logger = LoggerFactory.getLogger(ObjectCodecTest.class);

    private JsonFactory jsonFactory;
    private JsonParser jsonParser;

    private String content = """
            {"name":"hdhxby","age":18,"pet":{"name":"旺财","color":"WHITE"},"hobbies":["篮球","football"]}
            """;

    @BeforeEach
    public void setup() throws IOException {
        jsonFactory = new JsonFactoryBuilder().build();
    }

    @Test
    public void test1() throws IOException {
        jsonParser = jsonFactory.createParser(content);
        Person person = jsonParser.readValueAs(Person.class);
        System.out.println(person);
    }

    @Test
    public void test2() throws IOException {
        jsonParser = jsonFactory.createParser(content);
        jsonParser.setCodec(new PersonCodec());

        Person person = jsonParser.readValueAs(Person.class);
        System.out.println(person);
    }

    @AfterEach
    public void tearDown() throws IOException {
        jsonParser.close();
    }
}
