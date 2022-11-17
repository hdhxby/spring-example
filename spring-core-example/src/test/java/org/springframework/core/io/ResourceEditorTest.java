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
        // 支持标准URL如file:C:/myfile.txt，也支持classpath:myfile.txt
        // 同时还支持占位符形式

//        PropertyEditor editor = new ResourceEditor(new DefaultResourceLoader(), null, true);
        // StandardEnvironment可以为null
        PropertyEditor editor = new ResourceEditor(new DefaultResourceLoader(), new StandardEnvironment(), true);
        // file:形式本处略
        // editor.setAsText("file:...");
        // logger.debug(editor.getAsText());

        // classpath形式（注意：若文件不存在不会报错，而是输出null）
        editor.setAsText("classpath:application.yml");
        logger.debug(editor.getAsText()); // 输出带盘符的全路径
        isInstanceOf(ClassPathResource.class,editor.getValue());
        logger.debug("{}",editor.getValue()); // 输出带盘符的全路径

        editor.setValue(new ClassPathResource("application.yml"));
        logger.debug(editor.getAsText()); // 输出带盘符的全路径
        isInstanceOf(ClassPathResource.class,editor.getValue());
        logger.debug("{}",editor.getValue()); // 输出带盘符的全路径
//        System.setProperty("myFile.name", "application.yml");
//        editor.setAsText("classpath:${myFile.name}");
//        logger.debug(editor.getAsText()); // 结果同上
    }
}
