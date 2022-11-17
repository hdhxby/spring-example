package org.springframework.beans.support;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.beans.PropertyEditorRegistrySupport;
import org.springframework.beans.PropertyEditorRegistrySupportTest;
import org.springframework.beans.propertyeditors.CustomDateEditorTest;
import org.springframework.beans.propertyeditors.UUIDEditor;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

import java.beans.PropertyEditor;
import java.util.UUID;

public class ResourceEditorRegistrarTest {

    public static final Logger logger = LoggerFactory.getLogger(ResourceEditorRegistrarTest.class);

    @Test
    public void test6() {
        PropertyEditorRegistrySupport propertyEditorRegistry = new PropertyEditorRegistrySupport(){
            {
                registerDefaultEditors();
            }
        };
        ResourceEditorRegistrar resourceEditorRegistrar = new ResourceEditorRegistrar(new DefaultResourceLoader(),new StandardEnvironment());
        resourceEditorRegistrar.registerCustomEditors(propertyEditorRegistry);

        PropertyEditor propertyEditor = propertyEditorRegistry.getDefaultEditor(Resource.class);
        propertyEditor.setAsText("classpath:application.yml");

        propertyEditor.getValue();
    }
}
