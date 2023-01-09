package org.springframework.core.convert.support;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;

import static org.junit.jupiter.api.Assertions.*;

public class StringToBooleanConverterTest {

    public static final Logger logger = LoggerFactory.getLogger(StringToBooleanConverterTest.class);
    
    @Test
    public void test() {
        Converter<String, Boolean> converter = new StringToBooleanConverter();

        assertTrue(converter.convert("true"));
        assertTrue(converter.convert("1"));

        assertFalse(converter.convert("FalSe"));
        assertFalse(converter.convert("off"));

        assertNull(converter.convert(""));
    }

}
