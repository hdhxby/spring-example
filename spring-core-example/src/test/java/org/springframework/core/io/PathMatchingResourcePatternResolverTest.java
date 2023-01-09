package org.springframework.core.io;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.AntPathMatcher;
import x.y.z.service.impl.FooServiceImpl;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;


public class PathMatchingResourcePatternResolverTest {

    /**
     * ant风格的路径解析
     * 获取资源文件
     * @throws IOException
     */
    @Test
    public void testPathMatchingResourcePatternResolver() throws IOException {
        PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = pathMatchingResourcePatternResolver.getResources("x.y.z.**.*".replaceAll("\\.", File.separator));
        assertTrue(resources.length > 0);
    }
}
