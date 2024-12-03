package org.klobt;

import org.jetbrains.annotations.Contract;

import org.klobt.ast.*;
import org.klobt.operator.BinaryOperator;
import org.klobt.operator.Precedence;
import org.klobt.operator.UnaryOperator;
import org.klobt.token.*;
import org.klobt.value.*;

import java.util.ArrayList;
import java.util.HashMap;
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

    @Contract("_ -> fail")
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

    @SafeVarargs
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

    public PureNode expression() {
        PureNode node;

        if ((node = functionDefinition()) != null) {
            return node;
        } else if ((node = binary(Precedence.START)) != null) {
            return node;
        } else {
            return null;
        }
    }

    private PureNode literal() {
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
        } else if (current() instanceof BuiltinToken token) {
            int start = current().getStart();
            int end = current().getEnd();

            advance();

            return new LiteralNode(start, end, new BuiltinValue(token.getBuiltin()));
        } else if (current() instanceof NullToken) {
            int start = current().getStart();
            int end = current().getEnd();

            advance();

            return new LiteralNode(start, end, new NullValue());
        }

        return null;
    }

    private PureNode atom() {
        PureNode node;

        if ((node = literal()) != null) {
            return node;
        } else if (current() instanceof NameToken token) {
            int start = current().getStart();
            int end = current().getEnd();

            advance();

            return new VariableNode(start, end, token.getValue());
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

    private void keywordArguments(HashMap<String, PureNode> keywordArguments) {
        PureNode defaultValue;

        while (!isEOF() && current() instanceof NameToken nameToken && canPeek() && peek() instanceof AssignToken) {
            advance();
            advance();
            defaultValue = expression();

            if (defaultValue == null) {
                error("Expected expression");
            }
            keywordArguments.put(nameToken.getValue(), defaultValue);

            if (current() instanceof CommaToken) {
                advance();
            } else {
                return;
            }
        }
    }

    private ArgumentList<PureNode> argumentList() {
        List<PureNode> positionalArguments = new ArrayList<>();
        HashMap<String, PureNode> keywordArguments = new HashMap<>();
        PureNode argument;

        while (!isEOF() && !(current() instanceof NameToken && canPeek() && peek() instanceof AssignToken) && (argument = expression()) != null) {
            positionalArguments.add(argument);

            if (current() instanceof CommaToken) {
                advance();
            } else {
                return new ArgumentList<>(positionalArguments, keywordArguments);
            }
        }

        keywordArguments(keywordArguments);

        return new ArgumentList<>(positionalArguments, keywordArguments);
    }


    private PureNode functionDefinition() {
        List<String> positionalArguments = new ArrayList<>();
        HashMap<String, PureNode> keywordArguments = new HashMap<>();

        if (current() instanceof FunctionToken) {
            int start = current().getStart(), end = current().getEnd();
            advance();

            expect(LParenToken.class);
            advance();

            functionDefinitionArguments(positionalArguments, keywordArguments);

            expect(RParenToken.class);
            advance();

            Node body;

            if (current() instanceof LBraceToken) {
                advance();

                body = block();
                if (body == null) {
                    error("Expected block");
                }

                expect(RBraceToken.class);
                end = current().getEnd();
                advance();
            } else if ((body = expression()) != null) {
                end = body.getEnd();
            } else {
                error("Expected expression or block");
            }

            return new FunctionDefinitionNode(start, end, positionalArguments, keywordArguments, body);
        } else {
            return null;
        }
    }

    private void functionDefinitionArguments(List<String> positionalArguments, HashMap<String, PureNode> keywordArguments) {
        while (!isEOF() && !(current() instanceof NameToken && canPeek() && peek() instanceof AssignToken) && (current() instanceof NameToken name)) {
            advance();

            positionalArguments.add(name.getValue());

            if (current() instanceof CommaToken) {
                advance();
            } else {
                return;
            }
        }

        keywordArguments(keywordArguments);
    }

    private PureNode postfix() {
        int start, end;
        PureNode node = atom();
        if (node == null) {
            return null;
        }

        start = node.getStart();

        label:
        while (!isEOF()) {
            switch (current()) {
                case LParenToken _:
                    advance();

                    ArgumentList<PureNode> arguments = argumentList();

                    expect(RParenToken.class);
                    end = current().getEnd();
                    advance();

                    node = new CallNode(start, end, node, arguments);
                    break;
                case LBracketToken _:
                    advance();

                    PureNode index = expression();
                    if (index == null) {
                        error("Expected expression");
                    }

                    expect(RBracketToken.class);
                    end = current().getEnd();
                    advance();

                    node = new IndexNode(start, end, node, index);
                    break;
                case PeriodToken _:
                    advance();

                    expect(NameToken.class);
                    String memberName = ((NameToken) current()).getValue();
                    end = current().getEnd();
                    advance();

                    node = new MemberNode(start, end, node, memberName);
                    break;
                case ColonToken _:
                    advance();

                    expect(NameToken.class);
                    String methodName = ((NameToken) current()).getValue();
                    end = current().getEnd();
                    advance();

                    node = new MethodNode(start, end, node, methodName);
                    break;
                case null:
                default:
                    break label;
            }
        }

        return node;
    }

    private PureNode unary() {
        PureNode node;

        if (current() instanceof OperatorToken token && token.getValue() instanceof UnaryOperator operator) {
            int start = current().getStart();

            advance();
            expectMoreTokens();

            if ((node = unary()) != null) {
                return new UnaryOperationNode(start, node.getEnd(), node, operator);
            } else {
                error("Expected unary operator or postfix or atom");
            }
        } else if ((node = postfix()) != null) {
            return node;
        }

        return literal();
    }

    private PureNode binary(Precedence precedence) {
        PureNode left = precedence.isLast() ? unary() : binary(precedence.next());

        if (left == null) {
            return null;
        }

        while (!isEOF()) {
            if (current() instanceof OperatorToken token && token.getValue() instanceof BinaryOperator operator && operator.precedence() == precedence) {
                advance();
                expectMoreTokens();

                PureNode right = precedence.isLeftAssociative() ? (precedence.isLast() ? unary() : binary(precedence.next())) : binary(precedence);
                if (right == null) {
                    error("Unexpected token");
                }

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
        PureNode condition;

        if ((node = assignment()) != null) {
            expect(SemicolonToken.class);
            node.setEnd(current().getEnd());
            advance();

            return node;
        } else if ((node = expression()) != null) {
            expect(SemicolonToken.class);
            node.setEnd(current().getEnd());
            advance();

            return node;
        } else if (current() instanceof LBraceToken) {
            int start = current().getStart();

            advance();

            node = block();
            if (node == null) {
                error("Expected block");
            }

            expect(RBraceToken.class);
            node.setStart(start);
            node.setEnd(current().getEnd());
            advance();

            return node;
        } else if (current() instanceof IfToken) {
            Node ifStatement, elseStatement = null;
            int start = current().getStart(), end;

            advance();

            expect(LParenToken.class);
            advance();

            condition = expression();
            if (condition == null) {
                error("Expected expression");
            }

            expect(RParenToken.class);
            advance();

            ifStatement = statement();
            if (ifStatement == null) {
                error("Expected statement");
            }

            end = ifStatement.getEnd();

            if (current() instanceof ElseToken) {
                advance();

                elseStatement = statement();
                if (elseStatement == null) {
                    error("Expected statement");
                }

                end = elseStatement.getEnd();
            }

            return new BranchNode(start, end, condition, ifStatement, elseStatement);
        } else if (current() instanceof WhileToken) {
            Node body;
            int start = current().getStart();

            advance();

            expect(LParenToken.class);
            advance();

            condition = expression();
            if (condition == null) {
                error("Expected expression");
            }

            expect(RParenToken.class);
            advance();

            body = statement();
            if (body == null) {
                error("Expected statement");
            }

            return new WhileNode(start, body.getEnd(), condition, body);
        } else if (current() instanceof BreakToken) {
            int start = current().getStart(), end = current().getEnd();

            advance();
            expect(SemicolonToken.class);
            advance();

            return new BreakNode(start, end);
        } else if (current() instanceof ContinueToken) {
            int start = current().getStart(), end = current().getEnd();

            advance();
            expect(SemicolonToken.class);
            advance();

            return new ContinueNode(start, end);
        } else if (current() instanceof ReturnToken) {
            int start = current().getStart(), end = current().getEnd();
            advance();

            PureNode valueNode = expression();

            if (valueNode == null) {
                valueNode = new LiteralNode(start, end, new NullValue());
            }

            expect(SemicolonToken.class);
            advance();

            return new ReturnNode(start, valueNode.getEnd(), valueNode);
        } else {
            return null;
        }
    }

    public Node block() {
        List<Node> nodes = new ArrayList<>();
        Node node;

        while (!isEOF() && (node = statement()) != null) {
            nodes.add(node);
        }

        return new BlockNode(nodes.getFirst().getStart(), nodes.getLast().getEnd(), nodes);
    }

    public PureNode program() {
        Node node = block();

        return new ProgramNode(node.getStart(), node.getEnd(), node);
    }
}
