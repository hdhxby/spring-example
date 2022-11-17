package org.springframework.format.number;

import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.Locale;

public class NumberStyleFormatterTest {

    @Test
    public void test2() throws ParseException {
        NumberStyleFormatter formatter = new NumberStyleFormatter();

        double myNum = 1220.0455;
        System.out.println(formatter.print(myNum, Locale.getDefault()));

        formatter.setPattern("#.##");
        System.out.println(formatter.print(myNum, Locale.getDefault()));

        // 转换
        // Number parsedResult = formatter.parse("1,220.045", Locale.getDefault()); // java.text.ParseException: 1,220.045
        Number parsedResult = formatter.parse("1220.045", Locale.getDefault());
        System.out.println(parsedResult.getClass() + "-->" + parsedResult);
    }
}
