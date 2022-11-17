package org.springframework.format.datetime;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Locale;

public class DateFormatterTest {

    private Logger logger = LoggerFactory.getLogger(DateFormatterTest.class);

    @Test
    public void test1() {
        DateFormatter formatter = new DateFormatter();

        Date currDate = new Date();

        System.out.println("默认输出格式：" + formatter.print(currDate, Locale.CHINA));
        formatter.setIso(DateTimeFormat.ISO.DATE_TIME);
        System.out.println("指定ISO输出格式：" + formatter.print(currDate, Locale.CHINA));
        formatter.setPattern("yyyy-mm-dd HH:mm:ss");
        System.out.println("指定pattern输出格式：" + formatter.print(currDate, Locale.CHINA));
    }
}
