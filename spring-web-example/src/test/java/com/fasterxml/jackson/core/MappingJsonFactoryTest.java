package com.fasterxml.jackson.core;

import com.fasterxml.jackson.databind.MappingJsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MappingJsonFactoryTest {
    @Test
    public void test() {
        ObjectCodec objectCodec = MappingJsonFactory.builder().build().getCodec();
        assertTrue(objectCodec instanceof ObjectMapper);
    }
}
