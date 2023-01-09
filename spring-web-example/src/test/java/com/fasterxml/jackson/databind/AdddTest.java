package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import io.github.hdhxby.example.entity.PersonWithJsonFilter;
import org.junit.jupiter.api.Test;

public class AdddTest {
    @Test
    public void test() throws JsonProcessingException {
        SimpleFilterProvider simpleFilterProvider = new SimpleFilterProvider();
        simpleFilterProvider.addFilter("all",SimpleBeanPropertyFilter.serializeAll());
        simpleFilterProvider.addFilter("alias",SimpleBeanPropertyFilter.serializeAllExcept("alias"));

        JsonMapper jsonMapper = JsonMapper.builder()
                .filterProvider(simpleFilterProvider)
                .build();

        System.out.println(jsonMapper.writeValueAsString(new PersonWithJsonFilter()));


    }

}
