package org.springframework.expression.spel.standard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParserContext;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.ast.OpPlus;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import x.y.z.bean.Foo;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class SpelExpressionParserTest {

    SpelExpressionParser spelExpressionParser;
    @BeforeEach
    public void setup() {
        spelExpressionParser = new SpelExpressionParser();
    }
    @Test
    public void testTemplateParserContext(){
        spelExpressionParser = new SpelExpressionParser();
        // 算数运算
        SpelExpression spelExpression = spelExpressionParser.doParseExpression("1+1", new TemplateParserContext());
        assertInstanceOf(SpelExpression.class, spelExpression);
        assertInstanceOf(OpPlus.class, spelExpression.getAST());
    }

    @Test
    public void testStandardEvaluationContext(){
        // 上下文
        SpelExpression spelExpression = spelExpressionParser.doParseExpression("['foo']", new TemplateParserContext());
        StandardEvaluationContext evaluationContext = new StandardEvaluationContext();
        evaluationContext.setRootObject(Map.of("foo",3));
        spelExpression.setEvaluationContext(evaluationContext);
        assertEquals(3,spelExpression.getValue());
    }


    @Test
    public void testStandardEvaluationContext1(){
        spelExpressionParser = new SpelExpressionParser();
        // 上下文
        SpelExpression spelExpression = spelExpressionParser.doParseExpression("name", new TemplateParserContext());
        StandardEvaluationContext evaluationContext = new StandardEvaluationContext();
        evaluationContext.setRootObject(new Foo("foo"));
        spelExpression.setEvaluationContext(evaluationContext);
        assertEquals(1l,spelExpression.getValue());
    }

    @Test
    public void testStandardEvaluationContext2(){
        spelExpressionParser = new SpelExpressionParser();
        // 上下文
        SpelExpression spelExpression = spelExpressionParser.doParseExpression("#foo.name", new TemplateParserContext());
        StandardEvaluationContext evaluationContext = new StandardEvaluationContext();
        evaluationContext.setVariable("foo", new Foo("foo"));
        spelExpression.setEvaluationContext(evaluationContext);
        assertEquals(1l,spelExpression.getValue());
    }

    @Test
    public void testStandardEnvironment(){
        StandardEnvironment standardEnvironment = new StandardEnvironment();
        // 算数运算
        SpelExpressionParser spelExpressionParser = new SpelExpressionParser();
        // 上下文
        SpelExpression expression = spelExpressionParser.doParseExpression("[java_home]"
                , new TemplateParserContext());
        StandardEvaluationContext evaluationContext = new StandardEvaluationContext();
        expression.setEvaluationContext(evaluationContext);
        assertEquals(3,expression.getValue());
    }
}
