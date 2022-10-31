package org.springframework.core.io;

import org.junit.Test;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.AntPathMatcher;
import x.y.z.service.FooServiceImpl;

import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class PathMatchingResourcePatternResolverTest {

    /**
     * ant风格的路径解析
     * @throws IOException
     */
    @Test
    public void testPathMatchingResourcePatternResolver() throws IOException {
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        assertTrue(antPathMatcher.match("x.y.z.**.*", FooServiceImpl.class.getName()));
        PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = pathMatchingResourcePatternResolver.getResources(String.format("{}x.y.z.**", ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX));
        assertTrue(resources.length > 0);
    }
}
