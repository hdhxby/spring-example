package org.springframework.core.convert.support;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;

public class StringToBooleanConverterTest {

    public static final Logger logger = LoggerFactory.getLogger(StringToBooleanConverterTest.class);
    
    @Test
    public void test() {
        logger.debug("----------------StringToBooleanConverter---------------");
        Converter<String, Boolean> converter = new StringToBooleanConverter();

        // trueValues.add("true");
        // trueValues.add("on");
        // trueValues.add("yes");
        // trueValues.add("1");
        logger.debug("{}", converter.convert("true"));
        logger.debug("{}", converter.convert("1"));

        // falseValues.add("false");
        // falseValues.add("off");
        // falseValues.add("no");
        // falseValues.add("0");
        logger.debug("{}", converter.convert("FalSe"));
        logger.debug("{}", converter.convert("off"));
        // 注意：空串返回的是null
        logger.debug("{}", converter.convert(""));
    }
}
