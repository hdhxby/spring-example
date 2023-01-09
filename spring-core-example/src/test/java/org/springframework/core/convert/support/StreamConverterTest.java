package org.springframework.core.convert.support;

import org.junit.jupiter.api.Test;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalGenericConverter;
import org.springframework.core.convert.converter.ConverterFactory;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Stream;

public class StreamConverterTest {

    /**
     * 通过TypeDescriptor转换
     */
    @Test
    public void test2() {
        ConditionalGenericConverter converter = new StreamConverter(new DefaultConversionService());

        TypeDescriptor sourceTypeDesp = TypeDescriptor.valueOf(Set.class);
        TypeDescriptor targetTypeDesp = TypeDescriptor.valueOf(Stream.class);
        boolean matches = converter.matches(sourceTypeDesp, targetTypeDesp);
        System.out.println("是否能够转换：" + matches);

        // 执行转换
        Object convert = converter.convert(Collections.singleton(1), sourceTypeDesp, targetTypeDesp);
        System.out.println(convert);
        System.out.println(Stream.class.isAssignableFrom(convert.getClass()));
    }

    /**
     * 直接通过Class转换
     */
    @Test
    public void test3() {
        ConversionService conversionService = new DefaultConversionService();
        Stream<Integer> result = conversionService.convert(Collections.singleton(1), Stream.class);

        // 消费
        result.forEach(System.out::println);
        // result.forEach(System.out::println); //stream has already been operated upon or closed
    }
}
