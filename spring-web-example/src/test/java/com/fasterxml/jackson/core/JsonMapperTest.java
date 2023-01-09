package com.fasterxml.jackson.core;

import com.fasterxml.jackson.databind.json.JsonMapper;
import io.github.hdhxby.example.entity.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JsonMapperTest {
    private JsonMapper jsonMapper;

    @BeforeEach
    public void setup(){
        jsonMapper = JsonMapper
                .builder()
                .build();
    }

    @Test
    public void test() throws JsonProcessingException {
        Person person = new Person();
        String result = jsonMapper.writeValueAsString(person);
        person = jsonMapper.readValue(result, Person.class);
    }
}
