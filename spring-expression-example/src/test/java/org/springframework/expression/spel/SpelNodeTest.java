package org.springframework.expression.spel;

import org.junit.jupiter.api.Test;
import org.springframework.expression.spel.ast.IntLiteral;
import org.springframework.expression.spel.ast.OpPlus;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SpelNodeTest {

    /**
     * 抽象语法树
     */
    @Test
    public void testSpelNode() {
        IntLiteral one = new IntLiteral("1",1,2,1);
        IntLiteral two = new IntLiteral("1",2,3,1);
        SpelNode spelNode = new OpPlus(1,2,one,two);
        assertEquals(2, spelNode.getValue(null));
    }
}
