package org.springframework.beans.propertyeditors;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyEditor;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

public class CustomDateEditorTest {

    public static final Logger logger = LoggerFactory.getLogger(CustomDateEditorTest.class);

    @Test
    public void test3() {
        PropertyEditor editor = new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"),true);
        editor.setAsText("2020-11-30 09:10:10");
        logger.debug(editor.getAsText()); // 2020-11-30 09:10:10

        // null输出空串
        editor.setAsText(null);
        logger.debug(editor.getAsText());


        editor.setValue(Date.from(Instant.now()));
        logger.debug(editor.getAsText());
    }
}
