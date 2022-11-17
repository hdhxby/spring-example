package org.springframework.beans.propertyeditors;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyEditor;

public class CustomBooleanEditorTest {

    public static final Logger logger = LoggerFactory.getLogger(CustomBooleanEditorTest.class);
    
    @Test
    public void test() {
        PropertyEditor editor = new CustomBooleanEditor(true);

        // 这些都是true，不区分大小写
        editor.setAsText("trUe");
        logger.debug(editor.getAsText());
        editor.setAsText("on");
        logger.debug(editor.getAsText());
        editor.setAsText("yes");
        logger.debug(editor.getAsText());
        editor.setAsText("1");
        logger.debug(editor.getAsText());

        // 这些都是false（注意：null并不会输出为false，而是输出空串）
        editor.setAsText(null);
        logger.debug(editor.getAsText());
        editor.setAsText("fAlse");
        logger.debug(editor.getAsText());
        editor.setAsText("off");
        logger.debug(editor.getAsText());
        editor.setAsText("no");
        logger.debug(editor.getAsText());
        editor.setAsText("0");
        logger.debug(editor.getAsText());
    }
}
