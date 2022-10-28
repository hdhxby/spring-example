package org.springframework.core.env;

import lombok.extern.slf4j.Slf4j;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanExpressionContext;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.expression.StandardBeanExpressionResolver;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class StandardEnvironmentTest {
    private static final Logger logger = LoggerFactory.getLogger(StandardEnvironmentTest.class);

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

        assertEquals(System.getenv("JAVA_HOME"),environment.getProperty("JAVA_HOME"));
        assertEquals(System.getProperty("java.version"),environment.getProperty("java.version"));

        assertEquals("first",environment.getProperty("order"));
        assertEquals("first",environment.getPropertySources().get("first").getProperty("order"));
        assertEquals("last",environment.getPropertySources().get("last").getProperty("order"));
    }

    public void test2(){
        StandardEnvironment environment = new StandardEnvironment();
        Map<String, Object> env = environment.getSystemEnvironment();
        Map<String, Object> properties = environment.getSystemProperties();

        environment.getPropertySources().addFirst(new MapPropertySource("first",Map.of("order","first")));
        environment.getPropertySources().addLast(new PropertySource<Map<String,Object>>("last",Map.of("order","last")){

            @Override
            public Object getProperty(String name) {
                return source.get(name);
            }
        });
        logger.info(environment.getProperty("order"));
        logger.info("{}",environment.getPropertySources().get("first").getProperty("order"));
        logger.info("{}",environment.getPropertySources().get("last").getProperty("order"));
        logger.info("{}",new SpelExpressionParser().parseExpression("1+2").getValue());
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.registerSingleton("foo",new Foo("abc"));
        logger.info("{}",new StandardBeanExpressionResolver().evaluate("#{foo.name}",new BeanExpressionContext(beanFactory,null)));
    }

    class Foo{
        private String name;

        public Foo(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
