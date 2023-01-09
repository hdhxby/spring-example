package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import io.github.hdhxby.example.entity.Person;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DeserializationFeatureTest {

    private String content = """
        {
            "name":"martin",
            "password":"password",
            "age":18
        }
        """;
    /**
     *
     * @throws JsonProcessingException
     */
    @Test
    public void testDisableFAIL_ON_UNKNOWN_PROPERTIES() throws JsonProcessingException {
        JsonMapper jsonMapper = JsonMapper.builder()
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .build();

        Person person = jsonMapper.readValue(content, Person.class);
        assertNotNull(person);
    }

    /**
     * 瞬时
     * @throws JsonProcessingException
     */
    @Test
    public void testEnableFAIL_ON_UNKNOWN_PROPERTIES() throws JsonProcessingException {
        JsonMapper jsonMapper = JsonMapper.builder()
                .enable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .build();

        Person person = jsonMapper.readValue(content, Person.class);
        assertNotNull(person);

    }


}
