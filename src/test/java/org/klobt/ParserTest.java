package org.klobt;

import org.junit.jupiter.api.Test;
import org.klobt.ast.BinaryOperationNode;
import org.klobt.ast.LiteralNode;
import org.klobt.ast.UnaryOperationNode;
import org.klobt.operator.*;
import org.klobt.token.*;
import org.klobt.value.BooleanValue;
import org.klobt.value.NumberValue;
import org.klobt.value.StringValue;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {
    @Test
    public void testParseLiterals() {
        String input = "true 3.14 'test'";

        List<Token> tokens = new ArrayList<>();
        tokens.add(new BooleanToken(0, 4, true));
        tokens.add(new NumberToken(5, 9, 3.14));
        tokens.add(new StringToken(10, 16, "test"));

        Parser parser = new Parser(input, tokens);

        assertEquals(new LiteralNode(0, 4, new BooleanValue(true)), parser.expression());
        assertEquals(new LiteralNode(5, 9, new NumberValue(3.14)), parser.expression());
        assertEquals(new LiteralNode(10, 16, new StringValue("test")), parser.expression());
    }

    @Test
    public void testParseUnary() {
        String input = "-'test'";

        List<Token> tokens = new ArrayList<>();
        tokens.add(new OperatorToken(0, 1, new MinusOperator()));
        tokens.add(new StringToken(1, 7, "test"));

        Parser parser = new Parser(input, tokens);

        assertEquals(
                new UnaryOperationNode(
                        0, 7,
                        new LiteralNode(1, 7, new StringValue("test")),
                        new MinusOperator()
                ),
                parser.expression()
        );
    }

    @Test
    public void testParseBinary() {
        String input = "2 - 2 - -3 * 4^7^2";

        List<Token> tokens = new ArrayList<>();
        tokens.add(new NumberToken(0, 1, 2));
        tokens.add(new OperatorToken(2, 3, new MinusOperator()));
        tokens.add(new NumberToken(4, 5, 2));
        tokens.add(new OperatorToken(6, 7, new MinusOperator()));
        tokens.add(new OperatorToken(8, 9, new MinusOperator()));
        tokens.add(new NumberToken(9, 10, 3));
        tokens.add(new OperatorToken(11, 12, new MultiplyOperator()));
        tokens.add(new NumberToken(13, 14, 4));
        tokens.add(new OperatorToken(14, 15, new PowerOperator()));
        tokens.add(new NumberToken(15, 16, 7));
        tokens.add(new OperatorToken(16, 17, new PowerOperator()));
        tokens.add(new NumberToken(17, 18, 2));

        Parser parser = new Parser(input, tokens);

        assertEquals(
                new BinaryOperationNode(
                        0, 18,
                        new BinaryOperationNode(
                                0, 5,
                                new LiteralNode(0, 1, new NumberValue(2)),
                                new LiteralNode(4, 5, new NumberValue(2)),
                                new MinusOperator()
                        ),
                        new BinaryOperationNode(
                                8, 18,
                                new UnaryOperationNode(
                                        8, 10,
                                        new LiteralNode(9, 10, new NumberValue(3)),
                                        new MinusOperator()
                                ),
                                new BinaryOperationNode(
                                        13, 18,
                                        new LiteralNode(13, 14, new NumberValue(4)),
                                        new BinaryOperationNode(
                                                15, 18,
                                                new LiteralNode(15, 16, new NumberValue(7)),
                                                new LiteralNode(17, 18, new NumberValue(2)),
                                                new PowerOperator()
                                        ),
                                        new PowerOperator()
                                ),
                                new MultiplyOperator()
                        ),
                        new MinusOperator()
                ),
                parser.expression()
        );
    }
}
