package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import io.github.hdhxby.example.entity.Person;
import org.junit.jupiter.api.Test;

public class MapperFeatureTest {

    /**
     *
     * @throws JsonProcessingException
     */
    @Test
    public void testDisablePROPAGATE_TRANSIENT_MARKER() throws JsonProcessingException {
        JsonMapper jsonMapper = JsonMapper.builder()
                .disable(MapperFeature.PROPAGATE_TRANSIENT_MARKER)
                .build();

        Person person = new Person();
        person.setName("martin");
        person.setAge(18);
        String jsonStr = jsonMapper.writeValueAsString(person);
        System.out.println(jsonStr);

        jsonStr = "{\"name\":\"martin\",\"password\":\"password\",\"age\":18}";
        System.out.println(jsonMapper.readValue(jsonStr, Person.class));
    }

    /**
     * 瞬时
     * @throws JsonProcessingException
     */
    @Test
    public void testEnablePROPAGATE_TRANSIENT_MARKER() throws JsonProcessingException {
        JsonMapper jsonMapper = JsonMapper.builder()
                .enable(MapperFeature.PROPAGATE_TRANSIENT_MARKER)
                .build();

        Person person = new Person();
        String jsonStr = jsonMapper.writeValueAsString(person);
        System.out.println(jsonStr);

        jsonStr = "{\"name\":\"martin\",\"password\":\"password\",\"age\":18}";
        System.out.println(jsonMapper.readValue(jsonStr, Person.class));
    }


}
