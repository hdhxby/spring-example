package org.springframework.core.convert.support;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.ConverterFactory;

public class StringToNumberConverterFactoryTest {

    public static final Logger logger = LoggerFactory.getLogger(StringToNumberConverterFactoryTest.class);

    @Test
    public void test() {
        ConverterFactory<String, Number> converterFactory = new StringToNumberConverterFactory();
        // 注意：这里不能写基本数据类型。如int.class将抛错
        logger.debug("{}", converterFactory.getConverter(Integer.class).convert("1").getClass());
        logger.debug("{}", converterFactory.getConverter(Double.class).convert("1.1").getClass());
        logger.debug("{}", converterFactory.getConverter(Byte.class).convert("0x11").getClass());
    }

}
