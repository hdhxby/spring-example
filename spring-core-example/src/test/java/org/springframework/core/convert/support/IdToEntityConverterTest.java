package org.springframework.core.convert.support;

import org.junit.Test;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalGenericConverter;

public class IdToEntityConverterTest {

    @Test
    public void test() {
        System.out.println("----------------IdToEntityConverter---------------");
        ConditionalGenericConverter converter = new IdToEntityConverter(new DefaultConversionService());

        TypeDescriptor sourceTypeDesp = TypeDescriptor.valueOf(String.class);
        TypeDescriptor targetTypeDesp = TypeDescriptor.valueOf(Person.class);
        boolean matches = converter.matches(sourceTypeDesp, targetTypeDesp);
        System.out.println("是否能够转换：" + matches);

        // 执行转换
        Object convert = converter.convert("1", sourceTypeDesp, targetTypeDesp);
        System.out.println(convert);
    }

    public static class Person {

        private Long id;
        private String name;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        /**
         * 根据ID定位一个Person实例
         */
        public static Person findPerson(Long id) {
            // 一般根据id从数据库查，本处通过new来模拟
            Person person = new Person();
            person.setId(id);
            person.setName("YourBatman-byFindPerson");
            return person;
        }

    }
}
