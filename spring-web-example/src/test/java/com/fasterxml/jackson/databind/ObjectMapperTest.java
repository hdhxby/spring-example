package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.github.hdhxby.example.entity.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ObjectMapperTest {
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup(){
        objectMapper = new ObjectMapper();
    }

    @Test
    public void test() throws JsonProcessingException {
        Person person = new Person();
        String result = objectMapper.writeValueAsString(person);
        person = objectMapper.readValue(result,Person.class);
    }


    @Test
    public void testWrite() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        List<Integer> ids = new ArrayList<>();
        ids.add(1);
        ids.add(2);
        ids.add(3);

        String idsStr = objectMapper.writeValueAsString(ids);
        System.out.println(idsStr); // [1,2,3]
    }

    @Test
    public void testRead() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String idsStr = "[1,2,3]";

        List list = objectMapper.readValue(idsStr, List.class);
        System.out.println(list); // [1, 2, 3]
    }

    /**
     * 泛型擦除
     * @throws IOException
     */
    @Test
    public void testRead2() throws JsonProcessingException {
        String idsStr = "[1,2,3]";
        List<Long> list = objectMapper.readValue(idsStr, List.class);
        // 此处会报错
        Assertions.assertThrowsExactly(ClassCastException.class,()-> {Long l = list.get(0);});
    }

    /**
     * 范型擦除的解决方案
     * @throws JsonProcessingException
     */
    @Test
    public void testTypeReference() throws JsonProcessingException {
        String idsStr = "[1,2,3]";
        List<Long> list = objectMapper.readValue(idsStr, new TypeReference<List<Long>>() {});
        Long l = list.get(0);
    }

    public void test33(){
        objectMapper.enable(SerializationFeature.CLOSE_CLOSEABLE);

        objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT);

        objectMapper.enable(MapperFeature.PROPAGATE_TRANSIENT_MARKER);
    }
}
