package org.springframework.expression.spel.standard;

import org.junit.jupiter.api.Test;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.expression.Expression;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.ast.OpPlus;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class SpelParserConfigurationTest {
    @Test
    public void tesSpelParserConfiguration(){
        SpelParserConfiguration spelParserConfiguration = new SpelParserConfiguration();
        SpelExpressionParser spelExpressionParser = new SpelExpressionParser(spelParserConfiguration);
        // 算数运算
        SpelExpression spelExpression = spelExpressionParser.doParseExpression("1+1", new TemplateParserContext());
        assertInstanceOf(SpelExpression.class, spelExpression);
        assertInstanceOf(OpPlus.class, spelExpression.getAST());
    }

}
