package com.fasterxml.jackson.databind.node;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ObjectNodeTest {

    private static final Logger logger = LoggerFactory.getLogger(ObjectNodeTest.class);

    private JsonMapper jsonMapper;

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
    public void setup() throws IOException {
        jsonMapper = JsonMapper.builder()
                .build();
    }


    @Test
    public void testReadTree() throws JsonProcessingException {
        JsonNode jsonNode = jsonMapper.readTree(content);

        String color = jsonNode.get("pet").get("color").asText();
        logger.debug(color);
    }

    /**
     * 动态扩充属性
     * @throws IOException
     */
    @Test
    public void testWriteValue() throws IOException {
        JsonNode jsonNode = jsonMapper.readTree(content);
        String color = jsonNode.get("pet").get("color").asText();
        logger.debug(color);

        // 把json扩充属性
        // 强转为ObjectNode
        ObjectNode objectNode = (ObjectNode)jsonNode;
        objectNode.with("myDiy").put("contry", "China");
        jsonMapper.writeValue(System.out, objectNode);
    }


    /**
     * 动态扩充属性
     * @throws IOException
     */
    @Test
    public void testWriteValue1() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper()
                .enable(MapperFeature.PROPAGATE_TRANSIENT_MARKER);
        JsonNode jsonNode = objectMapper.readTree(content);
        String color = jsonNode.get("pet").get("color").asText();
        System.out.println(color);

        // 把json扩充属性
        // 强转为ObjectNode
        ObjectNode objectNode = (ObjectNode)jsonNode;
        objectNode.with("myDiy").put("contry", "China");
        objectMapper.writeValue(System.out, objectNode);
    }
}
