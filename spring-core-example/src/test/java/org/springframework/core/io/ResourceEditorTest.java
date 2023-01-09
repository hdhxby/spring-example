package org.springframework.core.io;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.util.Assert;

import java.beans.PropertyEditor;

import static org.springframework.util.Assert.isInstanceOf;

public class ResourceEditorTest {

    public static final Logger logger = LoggerFactory.getLogger(ResourceEditorTest.class);

    @Test
    public void test() {
        PropertyEditor editor = new ResourceEditor(new DefaultResourceLoader(), null, true);

        // classpath形式（注意：若文件不存在不会报错，而是输出null）
        editor.setAsText("classpath:applicationContext.xml");
        isInstanceOf(ClassPathResource.class,editor.getValue());
        logger.debug(editor.getAsText()); // 输出带盘符的全路径
    }
}
