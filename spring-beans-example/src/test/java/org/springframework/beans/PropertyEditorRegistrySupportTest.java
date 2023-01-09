package org.springframework.beans;

import org.junit.jupiter.api.Test;
import org.springframework.beans.propertyeditors.UUIDEditor;
import x.y.z.bean.Foo;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorSupport;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class PropertyEditorRegistrySupportTest {

    public class FooPropertyEditor extends PropertyEditorSupport {
        private Foo foo;

        @Override
        public String getAsText() {
            return foo.toString();
        }

        @Override
        public void setAsText(String text) throws IllegalArgumentException {
            foo = new Foo(text);
        }
    }

    @Test
    public void test() {
        PropertyEditorRegistry propertyEditorRegistry = new PropertyEditorRegistrySupport();
        propertyEditorRegistry.registerCustomEditor(Foo.class, new FooPropertyEditor());

        PropertyEditor fooPropertyEditor = propertyEditorRegistry.findCustomEditor(Foo.class, null);
        assertInstanceOf(FooPropertyEditor.class, fooPropertyEditor);
    }


    public class BarUUIDEditor extends UUIDEditor {

        private static final String SUFFIX = "bar_";

        @Override
        public String getAsText() {
            return SUFFIX.concat(super.getAsText());
        }

        @Override
        public void setAsText(String text) throws IllegalArgumentException {
            text = text.replace(SUFFIX, "");
            super.setAsText(text);
        }
    }

    class Bar {
        private UUID uuid;

        public UUID getUuid() {
            return uuid;
        }

        public void setUuid(UUID uuid) {
            this.uuid = uuid;
        }
    }
    @Test
    public void test6() {
        PropertyEditorRegistrySupport propertyEditorRegistry = new PropertyEditorRegistrySupport();
        // 通用的
        propertyEditorRegistry.registerCustomEditor(UUID.class, new UUIDEditor());
        // 专用的
        propertyEditorRegistry.registerCustomEditor(Bar.class, "uuid", new BarUUIDEditor());


        String uuidStr = "1-2-3-4-5";
        String personCatUuidStr = "bar_1-2-3-4-5";

        PropertyEditor customEditor = propertyEditorRegistry.findCustomEditor(UUID.class, null);
        customEditor.setAsText(uuidStr);
        System.out.println(customEditor.getAsText());

        customEditor = propertyEditorRegistry.findCustomEditor(Bar.class, "uuid");
        customEditor.setAsText(personCatUuidStr);
        System.out.println(customEditor.getAsText());
    }
}
