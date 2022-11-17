package org.springframework.format.number;

import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.Locale;

public class PercentStyleFormatterTest {
    @Test
    public void test3() throws ParseException {
        PercentStyleFormatter formatter = new PercentStyleFormatter();

        double myNum = 1220.0455;
        System.out.println(formatter.print(myNum, Locale.getDefault()));

        // 转换
        // Number parsedResult = formatter.parse("1,220.045", Locale.getDefault()); // java.text.ParseException: 1,220.045
        Number parsedResult = formatter.parse("122,005%", Locale.getDefault());
        System.out.println(parsedResult.getClass() + "-->" + parsedResult);
    }
}
