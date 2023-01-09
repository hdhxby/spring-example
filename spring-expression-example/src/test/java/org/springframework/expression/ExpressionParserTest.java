package org.springframework.expression;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.expression.spel.standard.SpelExpressionParser;

public class ExpressionParserTest {

    ExpressionParser spelExpressionParser;
    @BeforeEach
    public void setup() {
        spelExpressionParser = new SpelExpressionParser();
    }

    @Test
    public void testExpressionParser(){
        // 算数运算
        Expression expression = spelExpressionParser.parseExpression("1+1");
        assertEquals(2, expression.getValue());
        assertEquals("",spelExpressionParser.parseExpression("new String()").getValue());
        spelExpressionParser.parseExpression("new java.util.Date()").getValue();
    }

    @Test
    public void testExpressionParserWithParserContext(){
        // 算数运算
        Expression expression = spelExpressionParser.parseExpression("1+1", ParserContext.TEMPLATE_EXPRESSION);
        assertEquals(2, expression.getValue());
    }

}
