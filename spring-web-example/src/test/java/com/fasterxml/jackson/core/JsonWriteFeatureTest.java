package com.fasterxml.jackson.core;

import com.fasterxml.jackson.core.json.JsonWriteFeature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;

public class JsonWriteFeatureTest {

    private static final Logger logger = LoggerFactory.getLogger(JsonFactoryTest.class);

    private JsonGenerator jsonGenerator;

    @BeforeEach
    public void setup() throws IOException {
        jsonGenerator = new JsonFactoryBuilder()
                .build()
                .createGenerator(System.out);
    }

    @Test
    public void testJsonGenerator() throws IOException {
        jsonGenerator.writeStartObject(); // {
        // object
        jsonGenerator.writeStringField("name", "martin"); // "name" : "martin"
        jsonGenerator.writeNumberField("age", 18); // "age" : 18
        // object
        jsonGenerator.writeFieldName("pet"); // "pet":
        jsonGenerator.writeStartObject(); // {
        jsonGenerator.writeStringField("name", "snow");
        jsonGenerator.writeStringField("color", "WHITE");
        jsonGenerator.writeEndObject(); // }

        // arrays
        jsonGenerator.writeFieldName("hobbies"); // "hobbies" :
        jsonGenerator.writeStartArray(); // [
        jsonGenerator.writeString("basketball"); // "basketball"
        jsonGenerator.writeString("football"); // "football"
        jsonGenerator.writeEndArray(); // ]

        jsonGenerator.writeEndObject(); // }

        // 关闭IO流
        jsonGenerator.close();
    }


    /**
     * Feature测试
     * @throws IOException
     */
    @Test
    public void testFeature() throws IOException {
        JsonFactory jsonFactory = JsonFactory
                .builder()
                .build();

        jsonGenerator = jsonFactory.createGenerator(System.out)
                .disable(JsonGenerator.Feature.STRICT_DUPLICATE_DETECTION);
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("name", "martin");
        jsonGenerator.writeStringField("name", "martin");
        jsonGenerator.writeEndObject();
        jsonGenerator.flush();
        jsonGenerator.close();
        //
        jsonGenerator = jsonFactory.createGenerator(System.out)
                .enable(JsonGenerator.Feature.STRICT_DUPLICATE_DETECTION);
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("name", "martin");
        jsonGenerator.writeStringField("name", "martin");
        jsonGenerator.flush();
        jsonGenerator.close();
    }


    /**
     * Feature测试
     * @throws IOException
     */
    @Test
    public void testFeature2() throws IOException {
        JsonFactory jsonFactory = JsonFactory
                .builder()
                .build();

        BigDecimal bigDecimal = new BigDecimal("0.000000001");

        jsonGenerator = jsonFactory.createGenerator(System.out)
                .disable(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN);
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("decimal", bigDecimal);
        jsonGenerator.writeEndObject();
        jsonGenerator.flush();
        jsonGenerator.close();
        //
        jsonGenerator = jsonFactory.createGenerator(System.out)
                .enable(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN);
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("decimal", bigDecimal);
        jsonGenerator.writeEndObject();
        jsonGenerator.flush();
        jsonGenerator.close();
        System.out.println("x");
    }


    /**
     * Feature测试
     * @throws IOException
     */
    @Test
    public void testFeatureJsonWriteFeature() throws IOException {
        JsonFactory jsonFactory = JsonFactory
                .builder()
                .disable(JsonWriteFeature.QUOTE_FIELD_NAMES)
                .build();

        BigDecimal bigDecimal = new BigDecimal("0.000000001");

        jsonGenerator = jsonFactory.createGenerator(System.out);
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("decimal", bigDecimal);
        jsonGenerator.writeEndObject();
        jsonGenerator.flush();
        jsonGenerator.close();

        jsonFactory = JsonFactory
                .builder()
                .enable(JsonWriteFeature.QUOTE_FIELD_NAMES)
                .build();
        jsonGenerator = jsonFactory.createGenerator(System.out)
                .enable(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN);
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("decimal", bigDecimal);
        jsonGenerator.writeEndObject();
        jsonGenerator.flush();
        jsonGenerator.close();
        System.out.println("x");
    }
}
