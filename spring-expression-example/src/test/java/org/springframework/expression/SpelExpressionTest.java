package org.springframework.expression;

import static org.junit.Assert.*;
import org.junit.Test;
import org.springframework.context.expression.StandardBeanExpressionResolver;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.Map;

public class SpelExpressionTest {
    @Test
    public void a(){
        StandardEnvironment standardEnvironment = new StandardEnvironment();
        StandardBeanExpressionResolver standardBeanExpressionResolver = null;

        SpelExpressionParser spelExpressionParser = new SpelExpressionParser();
        assertEquals(2,spelExpressionParser.parseExpression("1+1").getValue());
        TemplateParserContext templateParserContext = new TemplateParserContext();
        org.springframework.expression.spel.standard.SpelExpression expression = (org.springframework.expression.spel.standard.SpelExpression)spelExpressionParser.parseExpression("['foo']");
        StandardEvaluationContext evaluationContext = new StandardEvaluationContext();
        evaluationContext.setRootObject(Map.of("foo","2"));
        expression.setEvaluationContext(evaluationContext);
        assertEquals(2,expression.getValue());
    }

    @Test
    public void testStandardBeanExpressionResolver(){
        StandardEnvironment standardEnvironment = new StandardEnvironment();
        StandardBeanExpressionResolver standardBeanExpressionResolver = null;

        SpelExpressionParser spelExpressionParser = new SpelExpressionParser();
        assertEquals(2,spelExpressionParser.parseExpression("1+1").getValue());
        TemplateParserContext templateParserContext = new TemplateParserContext();
        org.springframework.expression.spel.standard.SpelExpression expression = (org.springframework.expression.spel.standard.SpelExpression)spelExpressionParser.parseExpression("['foo']");
        StandardEvaluationContext evaluationContext = new StandardEvaluationContext();
        evaluationContext.setRootObject(Map.of("foo","2"));
        expression.setEvaluationContext(evaluationContext);
        assertEquals(2,expression.getValue());
    }
}
