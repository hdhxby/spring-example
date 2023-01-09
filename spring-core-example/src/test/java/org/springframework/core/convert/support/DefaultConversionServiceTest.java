package org.springframework.core.convert.support;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;

import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class DefaultConversionServiceTest {

    private Logger logger = LoggerFactory.getLogger(DefaultConversionServiceTest.class);

    @BeforeEach
    public void setup() {

    }
    @Test
    public void test(){
        ConversionService conversionService = new DefaultConversionService();
        assertEquals("1", conversionService.convert(1, String.class));
        assertInstanceOf(Integer.class , conversionService.convert("1", Integer.class));

        assertEquals("1,2", conversionService.convert(List.of(1,2), String.class));
        conversionService.convert(List.of(1,2), List.class);

        conversionService.convert("1,2", Set.class);

        conversionService.convert(List.of("1","2"), TypeDescriptor.collection(List.class, TypeDescriptor.valueOf(String.class)),
                TypeDescriptor.collection(Set.class, TypeDescriptor.valueOf(Integer.class)));
    }

}
