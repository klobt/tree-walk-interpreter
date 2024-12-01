package org.klobt;

import org.junit.jupiter.api.Test;
import org.klobt.ast.*;
import org.klobt.operator.*;
import org.klobt.token.*;
import org.klobt.value.BooleanValue;
import org.klobt.value.NumberValue;
import org.klobt.value.StringValue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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

    @Test
    public void testParseBinaryWithParentheses() {
        String input = "(2 + 2) * 3";

        List<Token> tokens = new ArrayList<>();
        tokens.add(new LParenToken(0, 1));
        tokens.add(new NumberToken(1, 2, 2));
        tokens.add(new OperatorToken(3, 4, new AddOperator()));
        tokens.add(new NumberToken(5, 6, 2));
        tokens.add(new RParenToken(6, 7));
        tokens.add(new OperatorToken(8, 9, new MultiplyOperator()));
        tokens.add(new NumberToken(10, 11, 3));

        Parser parser = new Parser(input, tokens);

        assertEquals(
                new BinaryOperationNode(
                        0, 11,
                        new BinaryOperationNode(
                                1, 6,
                                new LiteralNode(1, 2, new NumberValue(2)),
                                new LiteralNode(5, 6, new NumberValue(2)),
                                new AddOperator()
                        ),
                        new LiteralNode(10, 11, new NumberValue(3)),
                        new MultiplyOperator()
                ),
                parser.expression()
        );
    }

    @Test
    public void testAssignment() {
        String input = "x = 2 + 2;";

        List<Token> tokens = new ArrayList<>();
        tokens.add(new NameToken(0, 1, "x"));
        tokens.add(new AssignToken(2, 3));
        tokens.add(new NumberToken(4, 5, 2));
        tokens.add(new OperatorToken(6, 7, new AddOperator()));
        tokens.add(new NumberToken(8, 9, 2));
        tokens.add(new SemicolonToken(9, 10));

        Parser parser = new Parser(input, tokens);

        assertEquals(
                new AssignmentNode(
                        0, 9,
                        "x",
                        new BinaryOperationNode(
                                1, 6,
                                new LiteralNode(1, 2, new NumberValue(2)),
                                new LiteralNode(5, 6, new NumberValue(2)),
                                new AddOperator()
                        )
                ),
                parser.statement()
        );
    }

    @Test
    public void testBlock() {
        String input = "{1;2;3;}";

        List<Token> tokens = new ArrayList<>();
        tokens.add(new LBraceToken(0, 1));
        tokens.add(new NumberToken(1, 2, 1));
        tokens.add(new SemicolonToken(2, 3));
        tokens.add(new NumberToken(3, 4, 2));
        tokens.add(new SemicolonToken(4, 5));
        tokens.add(new NumberToken(5, 6, 3));
        tokens.add(new SemicolonToken(6, 7));
        tokens.add(new RBraceToken(7, 8));

        Parser parser = new Parser(input, tokens);

        List<Node> nodes = new ArrayList<>();

        nodes.add(new LiteralNode(1, 2, new NumberValue(1)));
        nodes.add(new LiteralNode(3, 4, new NumberValue(2)));
        nodes.add(new LiteralNode(5, 6, new NumberValue(3)));

        assertEquals(
                new BlockNode(
                        0, 8,
                        nodes
                ),
                parser.statement()
        );
    }

    @Test
    public void testIfStatement() {
        String input = "if(true){1;}else if(false){2;}else 3;";

        List<Token> tokens = new ArrayList<>();
        tokens.add(new IfToken(0, 2));
        tokens.add(new LParenToken(2, 3));
        tokens.add(new BooleanToken(3, 7, true));
        tokens.add(new RParenToken(7, 8));
        tokens.add(new LBraceToken(8, 9));
        tokens.add(new NumberToken(9, 10, 1));
        tokens.add(new SemicolonToken(10, 11));
        tokens.add(new RBraceToken(11, 12));
        tokens.add(new ElseToken(12, 13));
        tokens.add(new IfToken(14, 15));
        tokens.add(new LParenToken(16, 17));
        tokens.add(new BooleanToken(17, 18, false));
        tokens.add(new RParenToken(19, 20));
        tokens.add(new LBraceToken(21, 22));
        tokens.add(new NumberToken(22, 23, 2));
        tokens.add(new SemicolonToken(23, 24));
        tokens.add(new RBraceToken(24, 25));
        tokens.add(new ElseToken(25, 26));
        tokens.add(new NumberToken(27, 28, 3));
        tokens.add(new SemicolonToken(28, 29));

        Parser parser = new Parser(input, tokens);

        assertEquals(
                new BlockNode(
                        0, 0,
                        Collections.singletonList(
                                new BranchNode(
                                        0, 0,
                                        new LiteralNode(0, 0, new BooleanValue(true)),
                                        new BlockNode(
                                                0, 0,
                                                Collections.singletonList(new LiteralNode(0, 0, new NumberValue(1)))
                                        ),
                                        new BranchNode(
                                                0, 0,
                                                new LiteralNode(0, 0, new BooleanValue(false)),
                                                new BlockNode(
                                                        0, 0,
                                                        Collections.singletonList(new LiteralNode(0, 0, new NumberValue(2)))
                                                ),
                                                new LiteralNode(0, 0, new NumberValue(3))
                                        )
                                )
                        )
                ),
                parser.block()
        );
    }

    @Test
    public void testPostfix() {
        String input = "-func(1)[2]";

        List<Token> tokens = new ArrayList<>();
        tokens.add(new OperatorToken(0, 1, new MinusOperator()));
        tokens.add(new NameToken(1, 5, "func"));
        tokens.add(new LParenToken(5, 6));
        tokens.add(new NumberToken(6, 7, 1));
        tokens.add(new RParenToken(7, 8));
        tokens.add(new LBracketToken(8, 9));
        tokens.add(new NumberToken(9, 10, 2));
        tokens.add(new RBracketToken(10, 11));

        Parser parser = new Parser(input, tokens);

        assertEquals(
                new UnaryOperationNode(
                        0, 0,
                        new IndexNode(
                                0, 0,
                                new CallNode(
                                        0, 0,
                                        new VariableNode(0, 0, "func"),
                                        new ArgumentList<>(
                                                Collections.singletonList(new LiteralNode(0, 0, new NumberValue(1))),
                                                new HashMap<>()
                                        )
                                ),
                                new LiteralNode(0, 0, new NumberValue(2))
                        ),
                        new MinusOperator()
                ),
                parser.expression()
        );
    }

    @Test
    public void testCallArguments() {
        String input = "func(1,2,three=3)";

        List<Token> tokens = new ArrayList<>();
        tokens.add(new NameToken(0, 4, "func"));
        tokens.add(new LParenToken(4, 5));
        tokens.add(new NumberToken(5, 6, 1));
        tokens.add(new CommaToken(6, 7));
        tokens.add(new NumberToken(7, 8, 2));
        tokens.add(new CommaToken(8, 9));
        tokens.add(new NameToken(9, 14, "three"));
        tokens.add(new AssignToken(14, 15));
        tokens.add(new NumberToken(15, 16, 3));
        tokens.add(new RParenToken(16, 17));

        Parser parser = new Parser(input, tokens);

        List<Node> positionalArguments = new ArrayList<>();
        HashMap<String, Node> keywordArguments = new HashMap<>();

        positionalArguments.add(new LiteralNode(0, 0, new NumberValue(1)));
        positionalArguments.add(new LiteralNode(0, 0, new NumberValue(2)));
        keywordArguments.put("three", new LiteralNode(0, 0, new NumberValue(3)));

        assertEquals(
                new CallNode(
                        0, 0,
                        new VariableNode(0, 0, "func"),
                        new ArgumentList<>(
                                positionalArguments,
                                keywordArguments
                        )
                ),
                parser.expression()
        );
    }
}
