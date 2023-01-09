package org.springframework.beans.support;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.PropertyEditorRegistrySupport;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

import java.beans.PropertyEditor;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

/**
 * beanFactory.addPropertyEditorRegistrar()
 */
public class ResourceEditorRegistrarTest {

    public static final Logger logger = LoggerFactory.getLogger(ResourceEditorRegistrarTest.class);

    /**
     *
     */
    @Test
    public void test() {
        PropertyEditorRegistrySupport propertyEditorRegistry = new PropertyEditorRegistrySupport(){
            {
                registerDefaultEditors(); // 必须调用下这句
            }
        };

        ResourceEditorRegistrar resourceEditorRegistrar = new ResourceEditorRegistrar(new DefaultResourceLoader(),new StandardEnvironment());
        resourceEditorRegistrar.registerCustomEditors(propertyEditorRegistry);

        PropertyEditor propertyEditor = propertyEditorRegistry.findCustomEditor(Resource.class, null);
        propertyEditor.setAsText("classpath:applicationContext.xml");
        assertInstanceOf(Resource.class, propertyEditor.getValue());
    }
}
