package org.springframework.core.env;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class StandardEnvironmentTest {

    private static final Logger logger = LoggerFactory.getLogger(StandardEnvironmentTest.class);

    /**
     * 系统属性
     * 环境变量
     * 支持变量顺序
     */
    @Test
    public void testEnvironment(){
        StandardEnvironment environment = new StandardEnvironment();

        environment.getPropertySources().addFirst(new MapPropertySource("first",Map.of("order","first")));
        environment.getPropertySources().addLast(new PropertySource<Map<String,Object>>("last",Map.of("order","last")){
            @Override
            public Object getProperty(String name) {
                return source.get(name);
            }
        });

        assertNotNull(environment.getSystemEnvironment());
        assertNotNull(environment.getSystemProperties());
        // 系统变量,环境变量
        assertEquals(System.getenv("JAVA_HOME"),environment.getProperty("JAVA_HOME"));
        assertEquals(System.getProperty("java.version"),environment.getProperty("java.version"));
        // 变量顺序
        assertEquals("first",environment.getProperty("order"));
        assertEquals("first",environment.getPropertySources().get("first").getProperty("order"));
        assertEquals("last",environment.getPropertySources().get("last").getProperty("order"));
    }

}
