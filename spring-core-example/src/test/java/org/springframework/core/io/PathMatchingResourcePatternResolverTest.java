package org.springframework.core.io;

import org.junit.Test;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.AntPathMatcher;
import x.y.z.service.FooServiceImpl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class PathMatchingResourcePatternResolverTest {

    @Test
    public void testClassPathResource () throws IOException {
        ClassPathResource classPathResource = new ClassPathResource(FooServiceImpl.class.getName().replaceAll("\\.", File.separator)+".class");
        assertTrue(classPathResource.exists());
        assertNotNull(classPathResource.getInputStream());
    }
    /**
     * ant风格的路径解析
     * @throws IOException
     */
    @Test
    public void testPathMatchingResourcePatternResolver() throws IOException {
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        assertTrue(antPathMatcher.match("x.y.z.**.*", FooServiceImpl.class.getName()));
        PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = pathMatchingResourcePatternResolver.getResources("x.y.z.**.*".replaceAll("\\.", File.separator));
        assertTrue(resources.length > 0);
    }
}
