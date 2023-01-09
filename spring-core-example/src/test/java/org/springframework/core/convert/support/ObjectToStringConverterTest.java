package org.springframework.core.convert.support;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;

import static org.junit.jupiter.api.Assertions.*;

public class ObjectToStringConverterTest {

    public static final Logger logger = LoggerFactory.getLogger(ObjectToStringConverterTest.class);
    
    @Test
    public void test() {
        Converter<Object, String> converter = new ObjectToStringConverter();

        assertEquals("true", converter.convert(true));
        assertEquals("1", converter.convert(1));

        assertEquals("", converter.convert(""));
    }
}
