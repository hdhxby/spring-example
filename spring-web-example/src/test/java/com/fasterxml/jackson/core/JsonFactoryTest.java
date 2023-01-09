package com.fasterxml.jackson.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.hdhxby.example.entity.PersonCodec;
import io.github.hdhxby.example.entity.Pet;
import io.github.hdhxby.example.entity.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.ServiceLoader;

import static org.junit.jupiter.api.Assertions.*;

public class JsonFactoryTest {

    private static final Logger logger = LoggerFactory.getLogger(JsonFactoryTest.class);

    private JsonFactory jsonFactory;

    private String content = """
                {
                    "name":"martin",
                    "age":18,
                    "pet":{"name":"snow","color":"WHITE"},
                    "hobbies":[
                        "basketball",
                        "football"
                    ]
                }
                """;

    @BeforeEach
    public void setup(){
        jsonFactory = new JsonFactoryBuilder()
                .build();
    }

    /**
     * SPI机制
     */
    @Test
    public void testServiceLoader(){
        assertTrue(ServiceLoader
                .load(JsonFactory.class)
                .findFirst()
                .isPresent());

    }

    /**
     * 测试intern()方法
     */
    @Test
    public void testStringintern() {
        String str1 = "a";
        String str2 = "b";
        String str3 = "ab";
        String str4 = str1 + str2;
        String str5 = new String("ab");

        System.out.println(str5.equals(str3)); // true
        System.out.println(str5 == str3); // false

        // str5.intern()去常量池里找到了ab，所以直接返回常量池里的地址值了，因此是true
        System.out.println(str5.intern() == str3); // true
        System.out.println(str5.intern() == str4); // false
    }


    @Test
    public void test4() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Person person = objectMapper.readValue(content, Person.class);
        System.out.println(person);
    }

    @Test
    public void test5() throws IOException {
        Person person = new Person();
        person.setName("");
        person.setAge(1);
        person.setHobbies(List.of(""));
        Pet pet = new Pet();
        pet.setName("");
        pet.setColor(Color.WHITE);
        person.setPet(pet);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(System.out,person);
    }


    /**
     * 位运算开启关闭某些特性
     */
    @Test
    public void testFeature(){
        jsonFactory = new JsonFactoryBuilder()
                .build()
                .disable(JsonFactory.Feature.CANONICALIZE_FIELD_NAMES);
        assertFalse(jsonFactory.isEnabled(JsonFactory.Feature.CANONICALIZE_FIELD_NAMES));

        jsonFactory = new JsonFactoryBuilder()
                .build()
                .enable(JsonFactory.Feature.CANONICALIZE_FIELD_NAMES);
        assertTrue(jsonFactory.isEnabled(JsonFactory.Feature.CANONICALIZE_FIELD_NAMES));
    }

}
