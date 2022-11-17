package org.springframework.core.convert.support;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;

import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

public class DefaultConversionServiceTest {

    private Logger logger = LoggerFactory.getLogger(DefaultConversionServiceTest.class);

    @Test
    public void test(){
        ConversionService conversionService = new DefaultConversionService();
        logger.debug("{}", ZonedDateTime.now().toString());
        conversionService.convert(List.of("hello","word"), TypeDescriptor.valueOf(List.class),TypeDescriptor.valueOf(String.class));
        conversionService.convert(List.of("hello","word"), String.class);

    }
}
