package org.springframework.core.convert.support;

import org.junit.Test;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalGenericConverter;

public class ObjectToObjectConverterTest {
    @Test
    public void test4() {
        System.out.println("----------------ObjectToObjectConverter---------------");
        ConditionalGenericConverter converter = new ObjectToObjectConverter();

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setAddress("Peking");

        Object convert = converter.convert(customer, TypeDescriptor.forObject(customer), TypeDescriptor.valueOf(Person.class));
        System.out.println(convert);

        // ConversionService方式（实际使用方式）
        ConversionService conversionService = new DefaultConversionService();
        Person person = conversionService.convert(customer, Person.class);
        System.out.println(person);
    }

    // sourceClass
    public static class Customer {
        private Long id;
        private String address;

        public Customer() {
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public Person toPerson() {
            Person person = new Person();
            person.setId(getId());
            person.setName("YourBatman-".concat(getAddress()));
            return person;
        }
        /**
         * 方法名称可以是：valueOf、of、from
         */
        public static Person valueOf(Customer customer) {
            Person person = new Person();
            person.setId(customer.getId());
            person.setName("YourBatman-".concat(customer.getAddress()));
            return person;
        }
    }

    // tartgetClass
    public static class Person {
        private Long id;
        private String name;

        public Person() {
        }

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
    }
}
