package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import io.github.hdhxby.example.entity.Person;
import org.junit.jupiter.api.Test;

public class SerializationFeatureTest {

    /**
     *
     * @throws JsonProcessingException
     */
    @Test
    public void testDisablePROPAGATE_TRANSIENT_MARKER() throws JsonProcessingException {
        JsonMapper jsonMapper = JsonMapper.builder()
                .disable(SerializationFeature.INDENT_OUTPUT)
                .build();

        Person person = new Person();
        person.setName("martin");
        person.setAge(18);
        String jsonStr = jsonMapper.writeValueAsString(person);
        System.out.println(jsonStr);

    }

    /**
     * 瞬时
     * @throws JsonProcessingException
     */
    @Test
    public void testEnablePROPAGATE_TRANSIENT_MARKER() throws JsonProcessingException {
        JsonMapper jsonMapper = JsonMapper.builder()
                .enable(SerializationFeature.INDENT_OUTPUT)
                .build();

        Person person = new Person();
        String jsonStr = jsonMapper.writeValueAsString(person);
        System.out.println(jsonStr);
    }


}
