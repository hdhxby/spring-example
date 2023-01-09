package org.springframework.core.io;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.AntPathMatcher;
import x.y.z.service.impl.FooServiceImpl;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AntPathMatcherTest {

    /**
     * ant风格的路径解析
     * @throws IOException
     */
    @Test
    public void testPathMatchingResourcePatternResolver() throws IOException {
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        assertTrue(antPathMatcher.match("x.y.z.**.*", FooServiceImpl.class.getName()));
    }
}
