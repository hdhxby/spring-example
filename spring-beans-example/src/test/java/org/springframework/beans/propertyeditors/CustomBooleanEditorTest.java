package org.springframework.beans.propertyeditors;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyEditor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 字符串转Boolean
 * DataBinder里会用到
 */
public class CustomBooleanEditorTest {

    public static final Logger logger = LoggerFactory.getLogger(CustomBooleanEditorTest.class);
    
    @Test
    public void test() {
        CustomBooleanEditor editor = new CustomBooleanEditor(true);
        // true
        editor.setAsText("trUe");
        assertEquals(true, editor.getValue());
        editor.setAsText("on");
        assertEquals(true, editor.getValue());
        editor.setAsText("yes");
        assertEquals(true, editor.getValue());
        editor.setAsText("1");
        assertEquals(true, editor.getValue());
        // false
        editor.setAsText("fAlse");
        assertEquals(false, editor.getValue());
        editor.setAsText("off");
        assertEquals(false, editor.getValue());
        editor.setAsText("no");
        assertEquals(false, editor.getValue());
        editor.setAsText("0");
        assertEquals(false, editor.getValue());
        // 空字符串
        editor.setAsText(null);
        assertEquals("", editor.getValue());
    }
}
