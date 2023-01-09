package org.springframework.format.support;

import org.junit.jupiter.api.Test;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.Parser;
import org.springframework.format.Printer;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.util.NumberUtils;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

public class DefaultFormattingConversionServiceStandardBeanExpressionResolverTest {
    public class Person {

        private Integer id;
        private String name;

        public Person(Integer id, String name) {
            this.id = id;
            this.name = name;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    class IntegerPrinter implements Printer<Integer> {

        @Override
        public String print(Integer object, Locale locale) {
            object += 10;
            return object.toString();
        }
    }

    @Test
    public void test2() {
        FormattingConversionService formattingConversionService = new FormattingConversionService();
        FormatterRegistry formatterRegistry = formattingConversionService;
        // 说明：这里不使用DefaultConversionService是为了避免默认注册的那些转换器对结果的“干扰”，不方便看效果
        // ConversionService conversionService = new DefaultConversionService();
        ConversionService conversionService = formattingConversionService;

        // 注册格式化器
        formatterRegistry.addPrinter(new IntegerPrinter());

        // 最终均使用ConversionService统一提供服务转换
        System.out.println(conversionService.canConvert(Integer.class, String.class));
        System.out.println(conversionService.canConvert(Person.class, String.class));

        System.out.println(conversionService.convert(1, String.class));
        // 报错：No converter found capable of converting from type [cn.yourbatman.bean.Person] to type [java.lang.String]
//         System.out.println(conversionService.convert(new Person(1, "YourBatman"), String.class));
    }

    @Test
    public void test3() {
        FormattingConversionService formattingConversionService = new FormattingConversionService();
        FormatterRegistry formatterRegistry = formattingConversionService;
        // 说明：这里不使用DefaultConversionService是为了避免默认注册的那些转换器对结果的“干扰”，不方便看效果
        // ConversionService conversionService = new DefaultConversionService();
        ConversionService conversionService = formattingConversionService;

        // 注册格式化器
        formatterRegistry.addFormatterForFieldType(Person.class, new IntegerPrinter(), null);
        // 强调：此处绝不能使用lambda表达式代替，否则泛型类型丢失，结果将出错
        formatterRegistry.addConverter(new Converter<Person, Integer>() {
            @Override
            public Integer convert(Person source) {
                return source.getId();
            }
        });

        // 最终均使用ConversionService统一提供服务转换
        System.out.println(conversionService.canConvert(Person.class, String.class));
        System.out.println(conversionService.convert(new Person(1, "YourBatman"), String.class));
    }

    private static class IntegerParser implements Parser<Integer> {

        @Override
        public Integer parse(String text, Locale locale) throws ParseException {
            return NumberUtils.parseNumber(text, Integer.class);
        }
    }

    @Test
    public void test4() {
        FormattingConversionService formattingConversionService = new FormattingConversionService();
        FormatterRegistry formatterRegistry = formattingConversionService;
        ConversionService conversionService = formattingConversionService;

        // 注册格式化器
        formatterRegistry.addParser(new IntegerParser());

        System.out.println(conversionService.canConvert(String.class, Integer.class));
        System.out.println(conversionService.convert("1", Integer.class));
    }

    @Test
    public void test45() {
        FormattingConversionService formattingConversionService = new FormattingConversionService();
        FormatterRegistry formatterRegistry = formattingConversionService;
        ConversionService conversionService = formattingConversionService;

        // 注册格式化器
        formatterRegistry.addFormatterForFieldType(Person.class, new IntegerPrinter(), new IntegerParser());
        formatterRegistry.addConverter(new Converter<Integer, Person>() {
            @Override
            public Person convert(Integer source) {
                return new Person(source, "YourBatman");
            }
        });

        System.out.println(conversionService.canConvert(String.class, Person.class));
        System.out.println(conversionService.convert("1", Person.class));
    }


    @Test
    public void test49() {
        FormattingConversionService formattingConversionService = new FormattingConversionService();
        DateFormatter dateFormatter = new DateFormatter();
        formattingConversionService.addFormatter(dateFormatter);
        formattingConversionService.addParser(dateFormatter);
        System.out.println(formattingConversionService.convert(new Date(), String.class));
        System.out.println(formattingConversionService.convert("2022年12月10日", Date.class));
    }
}
