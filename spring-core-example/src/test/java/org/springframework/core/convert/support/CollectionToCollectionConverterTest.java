package org.springframework.core.convert.support;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalGenericConverter;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 创建完集合再通过ConversionService转换集合元素
 */
public class CollectionToCollectionConverterTest {

    public static final Logger logger = LoggerFactory.getLogger(CollectionToCollectionConverterTest.class);

    @Test
    public void test3() {
        ConditionalGenericConverter conditionalGenericConverter = new CollectionToCollectionConverter(new DefaultConversionService());
        // 将Collection转为Collection（注意：没有指定泛型类型哦）
        System.out.println(conditionalGenericConverter.getConvertibleTypes());

        List<String> sourceList = Arrays.asList("1", "2", "2", "3", "4");
        TypeDescriptor sourceTypeDesp = TypeDescriptor.collection(List.class, TypeDescriptor.valueOf(String.class));
        TypeDescriptor targetTypeDesp = TypeDescriptor.collection(Set.class, TypeDescriptor.valueOf(Integer.class));

        assertTrue(conditionalGenericConverter.matches(sourceTypeDesp, targetTypeDesp));
        Object convert = conditionalGenericConverter.convert(sourceList, sourceTypeDesp, targetTypeDesp);
        System.out.println(convert.getClass());
        System.out.println(convert);
    }

}
