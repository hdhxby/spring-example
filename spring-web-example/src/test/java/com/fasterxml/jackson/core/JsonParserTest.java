package com.fasterxml.jackson.core;

import com.fasterxml.jackson.core.json.JsonReadFeature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


public class JsonParserTest {

    private static final Logger logger = LoggerFactory.getLogger(JsonParserTest.class);

    private JsonParser jsonParser;

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
        jsonParser = new JsonFactoryBuilder()
                .build()
                .createParser(content);
    }

    @Test
    public void testJsonParser() throws IOException {
        JsonToken token;
        while ((token = jsonParser.nextToken()) != JsonToken.END_OBJECT) {
            if(token ==JsonToken.START_OBJECT){
                logger.debug("{}\t\t {}",token,jsonParser.getText());
            }
            String fieldname = jsonParser.getCurrentName();
            if ("name".equals(jsonParser.getCurrentName())) {
                token = jsonParser.nextToken();
                logger.debug("{}\t\t\t {} : {}",token, jsonParser.getCurrentName(), jsonParser.getText());
            } else if ("age".equals(jsonParser.getCurrentName())) {
                token = jsonParser.nextToken();
                logger.debug("{}\t\t {} : {}",token, jsonParser.getCurrentName(), jsonParser.getText());
            } else if ("pet".equals(jsonParser.getCurrentName())) {
                token = jsonParser.nextToken();
                while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
                    String dogFieldName = jsonParser.getCurrentName();
                    if ("name".equals(dogFieldName)) {
                        logger.debug("{}\t\t\t\t {} : {}",token, jsonParser.getCurrentName(), jsonParser.getText());
                    } else if ("color".equals(dogFieldName)) {
                        token = jsonParser.nextToken();
                        logger.debug("{}\t\t\t\t {} : {}",token, jsonParser.getCurrentName(), jsonParser.getText());
                    }
                    logger.debug("{}\t\t {}",token,jsonParser.getText());
                }
            } else if ("hobbies".equals(jsonParser.getCurrentName())) {
                logger.debug("{}\t\t {} : {}",token, jsonParser.getCurrentName(), jsonParser.getText());
                while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                    logger.debug("{}\t\t {},",token, jsonParser.getText());
                }
                logger.debug("{}\t\t {}",token,jsonParser.getText());
            }
        }
        logger.debug("{}\t\t {}",token,jsonParser.getText());
    }

    /**
     * Feature测试
     * @throws IOException
     */
    @Test
    public void testDisableSTRICT_DUPLICATE_DETECTION() throws IOException {
        JsonFactory jsonFactory = JsonFactory
                .builder()
                .build();
        // 输出写到控制台
        String content = "{\"name\" : \"martin\",\"name\" : \"Perter\"}";
        JsonParser jsonParser = jsonFactory.createParser(content);

        jsonParser.disable(JsonParser.Feature.STRICT_DUPLICATE_DETECTION);
        while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
            String fieldname = jsonParser.getCurrentName();
            if ("name".equals(fieldname)) {
                System.out.println(jsonParser.nextTextValue());
            }
        }
        jsonParser.close();
    }


    /**
     * Feature测试
     * @throws IOException
     */
    @Test
    public void testEnableSTRICT_DUPLICATE_DETECTION() throws IOException {
        JsonFactory jsonFactory = JsonFactory
                .builder()
                .build();
        //
        jsonParser = jsonFactory.createParser(content);
        jsonParser.enable(JsonParser.Feature.STRICT_DUPLICATE_DETECTION);
        while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
            String fieldname = jsonParser.getCurrentName();
            if ("name".equals(fieldname)) {
                System.out.println(jsonParser.nextTextValue());
            }
        }
        jsonParser.close();
    }

}
