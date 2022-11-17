package org.springframework.format.number;

import org.junit.jupiter.api.Test;

import java.math.RoundingMode;
import java.text.ParseException;
import java.util.Locale;

public class CurrencyStyleFormatterTest {

    @Test
    public void test3() throws ParseException {
        CurrencyStyleFormatter formatter = new CurrencyStyleFormatter();

        double myNum = 1220.0455;
        System.out.println(formatter.print(myNum, Locale.getDefault()));

        System.out.println("--------------定制化--------------");
        // 指定货币种类（如果你知道的话）
        // formatter.setCurrency(Currency.getInstance(Locale.getDefault()));
        // 指定所需的分数位数。默认是2
        formatter.setFractionDigits(1);
        // 舍入模式。默认是RoundingMode#UNNECESSARY
        formatter.setRoundingMode(RoundingMode.CEILING);
        // 格式化数字的模版
        formatter.setPattern("#.#¤¤");

        System.out.println(formatter.print(myNum, Locale.getDefault()));

        // 转换
        // Number parsedResult = formatter.parse("￥1220.05", Locale.getDefault());
        Number parsedResult = formatter.parse("1220.1CNY", Locale.getDefault());
        System.out.println(parsedResult.getClass() + "-->" + parsedResult);
    }
}
