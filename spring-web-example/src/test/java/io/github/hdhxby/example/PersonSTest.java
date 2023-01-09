package io.github.hdhxby.example;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import io.github.hdhxby.example.entity.Person;
import io.github.hdhxby.example.entity.PersonStdSerializer;
import io.github.hdhxby.example.entity.PersonWithJsonSerialize;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class PersonSTest {

    private JsonMapper.Builder builder;

    public void setup() {
        builder = JsonMapper.builder();
    }

    @Test
    public void test() throws IOException {
        JsonMapper jsonMapper = builder.build();

        jsonMapper.writeValue(System.out,new Person<>());
    }

    /**
     * 基于Module
     * @throws IOException
     */
    @Test
    public void testWithSimpleModule() throws IOException {
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Person.class, new PersonStdSerializer(Person.class));

        JsonMapper jsonMapper = builder
                .addModule(simpleModule)
                .build();

        jsonMapper.writeValue(System.out,new Person<>());
    }

    /**
     * 基于注解
     * @throws IOException
     */
    @Test
    public void testWithJsonSerialize() throws IOException {
        JsonMapper jsonMapper = builder.build();

        jsonMapper.writeValue(System.out,new PersonWithJsonSerialize());
    }
}
