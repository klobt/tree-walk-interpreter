package org.klobt;

import org.klobt.ast.*;
import org.klobt.operator.BinaryOperator;
import org.klobt.operator.Precedence;
import org.klobt.operator.UnaryOperator;
import org.klobt.token.*;
import org.klobt.value.*;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    private final String input;
    private final List<Token> tokens;
    private int currentTokenIndex;
    private int savedIndex;

    public Parser(String input, List<Token> tokens) {
        this.input = input;
        this.tokens = tokens;
        currentTokenIndex = 0;
        savedIndex = 0;
    }

    private Token current() {
        return tokens.get(currentTokenIndex);
    }

    private Token peek() {
        return tokens.get(currentTokenIndex + 1);
    }

    private boolean canPeek() {
        return currentTokenIndex + 1 < tokens.size();
    }

    private void advance() {
        currentTokenIndex++;
    }

    private void save() {
        savedIndex = currentTokenIndex;
    }

    private void load() {
        currentTokenIndex = savedIndex;
    }

    private boolean isEOF() {
        return currentTokenIndex >= tokens.size();
    }

    private void error(String message) {
        int start, end;

        if (isEOF()) {
            start = input.length();
            end = input.length() + 1;
        } else {
            start = current().getStart();
            end = current().getEnd();
        }

        throw new Error(input, start, end, message);
    }

    private void expect(Class<? extends Token>... cs) {
        for (Class<? extends Token> c : cs) {
            if (c.isInstance(current())) {
                return;
            }
        }

        StringBuilder what = new StringBuilder();

        for (Class<? extends Token> c : cs) {
            String name = c.getSimpleName()
                    .replaceAll("(a-z)(A-Z)", "$1 $2")
                    .replaceFirst("token$", "")
                    .toLowerCase();

            if (!what.isEmpty()) {
                what.append(" or ");
            }

            what.append(name);
        }

        error("Expected " + what);
    }

    private void expectMoreTokens() {
        if (isEOF()) {
            error("Unexpected end of file");
        }
    }

    public Node expression() {
        return binary(Precedence.START);
    }

    private Node literal() {
        if (current() instanceof BooleanToken token) {
            int start = current().getStart();
            int end = current().getEnd();

            advance();

            return new LiteralNode(start, end, new BooleanValue(token.getValue()));
        } else if (current() instanceof NumberToken token) {
            int start = current().getStart();
            int end = current().getEnd();

            advance();

            return new LiteralNode(start, end, new NumberValue(token.getValue()));
        } else if (current() instanceof StringToken token) {
            int start = current().getStart();
            int end = current().getEnd();

            advance();

            return new LiteralNode(start, end, new StringValue(token.getValue()));
        } else if (current() instanceof NameToken token) {
            int start = current().getStart();
            int end = current().getEnd();

            advance();

            return new VariableNode(start, end, token.getValue());
        }

        return null;
    }

    private Node atom() {
        Node node;

        if ((node = literal()) != null) {
            return node;
        } else if (current() instanceof LParenToken) {
            advance();

            node = expression();
            if (node == null) {
                error("Expected expression");
            }

            expect(RParenToken.class);
            advance();

            return node;
        }

        return null;
    }

    private Node unary() {
        Node node;

        if (current() instanceof OperatorToken token && token.getValue() instanceof UnaryOperator operator) {
            int start = current().getStart();

            advance();
            expectMoreTokens();

            if ((node = unary()) != null) {
                return new UnaryOperationNode(start, node.getEnd(), node, operator);
            } else {
                error("Expected unary operator or literal");
            }
        } else if ((node = atom()) != null) {
            return node;
        }

        return literal();
    }

    private Node binary(Precedence precedence) {
        Node left = precedence.isLast() ? unary() : binary(precedence.next());

        if (left == null) {
            return null;
        }

        while (!isEOF()) {
            if (current() instanceof OperatorToken token && token.getValue() instanceof BinaryOperator operator && operator.precedence() == precedence) {
                advance();
                expectMoreTokens();

                Node right = precedence.isLeftAssociative() ? (precedence.isLast() ? unary() : binary(precedence.next())) : binary(precedence);

                left = new BinaryOperationNode(left.getStart(), right.getEnd(), left, right, operator);
            } else {
                break;
            }
        }

        return left;
    }

    private Node assignment() {
        save();

        if (current() instanceof NameToken token) {
            advance();

            if (current() instanceof AssignToken) {
                advance();

                Node node = expression();

                if (node != null) {
                    return new AssignmentNode(token.getStart(), node.getEnd(), token.getValue(), node);
                }
            }
        }

        load();

        return null;
    }

    public Node statement() {
        Node node;

        if ((node = assignment()) != null) {
            return node;
        } else if ((node = expression()) != null) {
            return node;
        } else {
            return null;
        }
    }
}
