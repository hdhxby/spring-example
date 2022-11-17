package org.springframework.beans;

import org.junit.jupiter.api.Test;
import org.springframework.beans.propertyeditors.UUIDEditor;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorSupport;
import java.util.UUID;

public class PropertyEditorRegistrySupportTest {

    public abstract class Animal {
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
    }

    public class Cat extends Animal {

        private UUID uuid;

        public UUID getUuid() {
            return uuid;
        }

        public void setUuid(UUID uuid) {
            this.uuid = uuid;
        }
    }

    public class Person {
        private Long id;
        private String name;
        private Cat cat;

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

        public Cat getCat() {
            return cat;
        }

        public void setCat(Cat cat) {
            this.cat = cat;
        }
    }

    public class PersonCatUUIDEditor extends UUIDEditor {

        private static final String SUFFIX = "_path";

        @Override
        public String getAsText() {
            return super.getAsText().concat(SUFFIX);
        }

        @Override
        public void setAsText(String text) throws IllegalArgumentException {
            text = text.replace(SUFFIX, "");
            super.setAsText(text);
        }
    }
    public class AnimalPropertyEditor extends PropertyEditorSupport {

        @Override
        public String getAsText() {
            return null;
        }

        @Override
        public void setAsText(String text) throws IllegalArgumentException {
        }
    }

    @Test
    public void test() {
        PropertyEditorRegistry propertyEditorRegistry = new PropertyEditorRegistrySupport();
        propertyEditorRegistry.registerCustomEditor(Animal.class, new AnimalPropertyEditor());

        // 付类型、子类型均可匹配上对应的编辑器
        PropertyEditor customEditor1 = propertyEditorRegistry.findCustomEditor(Cat.class, null);
        PropertyEditor customEditor2 = propertyEditorRegistry.findCustomEditor(Animal.class, null);
        System.out.println(customEditor1 == customEditor2);
        System.out.println(customEditor1.getClass().getSimpleName());
    }

    @Test
    public void test6() {
        PropertyEditorRegistrySupport propertyEditorRegistry = new PropertyEditorRegistrySupport();
        // 通用的
        propertyEditorRegistry.registerCustomEditor(UUID.class, new UUIDEditor());
        // 专用的
        propertyEditorRegistry.registerCustomEditor(Person.class, "cat.uuid", new PersonCatUUIDEditor());


        String uuidStr = "1-2-3-4-5";
        String personCatUuidStr = "1-2-3-4-5_path";

        PropertyEditor customEditor = propertyEditorRegistry.findCustomEditor(UUID.class, null);
        // customEditor.setAsText(personCatUuidStr); // 抛异常：java.lang.NumberFormatException: For input string: "5_YourBatman"
        customEditor.setAsText(uuidStr);
        System.out.println(customEditor.getAsText());

        customEditor = propertyEditorRegistry.findCustomEditor(Person.class, "cat.uuid");
        customEditor.setAsText(personCatUuidStr);
        System.out.println(customEditor.getAsText());
    }
}
