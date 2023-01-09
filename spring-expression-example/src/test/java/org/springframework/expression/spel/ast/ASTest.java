package org.springframework.expression.spel.ast;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
/**
 * 抽象语法树
 */
public class ASTest {
//    Logger logger = LoggerFactory.getLogger(ASTest.class);

    @Test
    public void testSpelNode() {
        Literal one = new IntLiteral("1",1,2,1);
        Literal two = new IntLiteral("1",2,3,1);
        Operator operator = new OpPlus(1,2,one,two);
        assertEquals("(1 + 1)",operator.toStringAST());
        assertEquals(2, operator.getValue(null));
    }
}
