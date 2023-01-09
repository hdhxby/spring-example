package org.springframework.core.io;

import org.junit.jupiter.api.Test;
import x.y.z.manager.impl.FooManagerImpl;
import x.y.z.service.impl.FooServiceImpl;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClassPathResourceTest {

    @Test
    public void testClassPathResource () throws IOException {
        ClassPathResource classPathResource = new ClassPathResource("applicationContext.xml");
        assertTrue(classPathResource.exists());
    }
}
