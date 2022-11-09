package org.springframework.core.convert.support;

import org.junit.Test;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalGenericConverter;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class CollectionToCollectionConverterTest {

    @Test
    public void test3() {
        System.out.println("----------------CollectionToCollectionConverter---------------");
        ConditionalGenericConverter conditionalGenericConverter = new CollectionToCollectionConverter(new DefaultConversionService());
        // 将Collection转为Collection（注意：没有指定泛型类型哦）
        System.out.println(conditionalGenericConverter.getConvertibleTypes());

        List<String> sourceList = Arrays.asList("1", "2", "2", "3", "4");
        TypeDescriptor sourceTypeDesp = TypeDescriptor.collection(List.class, TypeDescriptor.valueOf(String.class));
        TypeDescriptor targetTypeDesp = TypeDescriptor.collection(Set.class, TypeDescriptor.valueOf(Integer.class));

        System.out.println(conditionalGenericConverter.matches(sourceTypeDesp, targetTypeDesp));
        Object convert = conditionalGenericConverter.convert(sourceList, sourceTypeDesp, targetTypeDesp);
        System.out.println(convert.getClass());
        System.out.println(convert);
    }
}
