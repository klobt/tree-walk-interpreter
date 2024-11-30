package org.klobt;

import org.klobt.operator.*;
import org.klobt.token.*;

import java.util.ArrayList;
import java.util.List;

public class Tokenizer {
    private boolean isWhitespace(char ch) {
        return ch == ' ' || ch == '\t' || ch == '\n' || ch == '\r';
    }

    private boolean isDigit(char ch) {
        return ch >= '0' && ch <= '9';
    }

    private boolean isAlphabetic(char ch) {
        return (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z');
    }

    private boolean isStartOfName(char ch) {
        return isAlphabetic(ch) || ch == '_';
    }

    private boolean isName(char ch) {
        return isStartOfName(ch) || isDigit(ch);
    }

    private boolean isSpecial(char ch) {
        return ch >= '!' && ch <= '~' && !isName(ch);
    }

    public List<Token> tokenize(String input) {
        List<Token> tokens = new ArrayList<>();
        int i = 0;

        while (i < input.length()) {
            if (i + 1 < input.length() && input.charAt(i) == '-' && input.charAt(i + 1) == '-') {
                i += 2;

                if (i + 1 < input.length() && input.charAt(i) == '[' && input.charAt(i + 1) == '[') {
                    i += 2;

                    while (i < input.length() && !(i + 1 < input.length() && input.charAt(i) == ']' && input.charAt(i + 1) == ']')) {
                        i++;
                    }

                    if (i < input.length()) {
                        i += 2;
                    }
                } else {
                    while (i < input.length() && input.charAt(i) != '\n') {
                        i++;
                    }
                }
            } else if (input.charAt(i) == '"' || input.charAt(i) == '\'') {
                char quoteType = input.charAt(i);
                int start = i;
                i++;

                StringBuilder stringBuilder = new StringBuilder();
                while (i < input.length() && input.charAt(i) != quoteType) {
                    if (input.charAt(i) == '\\' && i + 1 < input.length()) {
                        i++;
                        switch (input.charAt(i)) {
                            case 'n':
                                stringBuilder.append('\n');
                                break;
                            case 't':
                                stringBuilder.append('\t');
                                break;
                            case 'r':
                                stringBuilder.append('\r');
                                break;
                            case '\"':
                                stringBuilder.append('\"');
                                break;
                            case '\'':
                                stringBuilder.append('\'');
                                break;
                            case '\\':
                                stringBuilder.append('\\');
                                break;
                            default:
                                stringBuilder.append(input.charAt(i));
                                break;
                        }
                    } else {
                        stringBuilder.append(input.charAt(i));
                    }
                    i++;
                }

                if (i >= input.length() || input.charAt(i) != quoteType) {
                    throw new Error(input, start, i, "Unterminated string literal");
                }

                i++;
                tokens.add(new StringToken(start, i, stringBuilder.toString()));
            } else if (i + 1 < input.length() && input.charAt(i) == '[' && input.charAt(i + 1) == '[') {
                i += 2;

                int start = i;

                while (i < input.length() && !(i + 1 < input.length() && input.charAt(i) == ']' && input.charAt(i + 1) == ']')) {
                    i++;
                }

                if (i >= input.length()) {
                    throw new Error(input, i - 1, i, "Expected ]]");
                }

                tokens.add(new StringToken(start, i, input.substring(start, i)));

                i += 2;
            } else if (isWhitespace(input.charAt(i))) {
                i++;
            } else if (isDigit(input.charAt(i))) {
                int start = i;

                double value = 0;

                while (i < input.length() && isDigit(input.charAt(i))) {
                    value *= 10;
                    value += input.charAt(i) - '0';
                    i++;
                }

                if (i < input.length() && input.charAt(i) == '.') {
                    i++;

                    int placesAfterPoint = 0;

                    while (i < input.length() && input.charAt(i) >= '0' && input.charAt(i) <= '9') {
                        value *= 10;
                        value += input.charAt(i) - '0';
                        placesAfterPoint++;
                        i++;
                    }

                    for (int j = 0; j < placesAfterPoint; j++) {
                        value /= 10;
                    }
                }

                if (i < input.length() && input.charAt(i) == 'e') {
                    i++;

                    if (i >= input.length()) {
                        throw new Error(input, i, i + 1, "Expected +, -, or a number");
                    }

                    int sign = 1;

                    if (input.charAt(i) == '+') {
                        i++;
                    } else if (input.charAt(i) == '-') {
                        sign = -1;
                        i++;
                    }

                    int exponent = 0;

                    if (!isDigit(input.charAt(i))) {
                        throw new Error(input, i, i + 1, "Expected +, -, or a number");
                    }

                    while (i < input.length() && isDigit(input.charAt(i))) {
                        exponent *= 10;
                        exponent += input.charAt(i) - '0';
                        i++;
                    }

                    for (int j = 0; j < exponent; j++) {
                        if (sign > 0) {
                            value *= 10;
                        } else {
                            value /= 10;
                        }
                    }
                }

                tokens.add(new NumberToken(start, i, value));
            } else if (isStartOfName(input.charAt(i))) {
                int start = i;

                while (i < input.length() && isName(input.charAt(i))) {
                    i++;
                }

                String value = input.substring(start, i);

                switch (value) {
                    case "false":
                        tokens.add(new BooleanToken(start, i, false));
                        break;
                    case "true":
                        tokens.add(new BooleanToken(start, i, true));
                        break;
                    default:
                        tokens.add(new NameToken(start, i, value));
                }
            } else if (isSpecial(input.charAt(i))) {
                int start = i;

                while (i < input.length() && isSpecial(input.charAt(i))) {
                    i++;
                }

                String operator = input.substring(start, i);

                switch (operator) {
                    case "!":
                        tokens.add(new OperatorToken(start, i, new NotOperator()));
                        break;
                    case "&&":
                        tokens.add(new OperatorToken(start, i, new AndOperator()));
                        break;
                    case "||":
                        tokens.add(new OperatorToken(start, i, new OrOperator()));
                        break;
                    case "+":
                        tokens.add(new OperatorToken(start, i, new AddOperator()));
                        break;
                    case "-":
                        tokens.add(new OperatorToken(start, i, new MinusOperator()));
                        break;
                    case "*":
                        tokens.add(new OperatorToken(start, i, new MultiplyOperator()));
                        break;
                    case "/":
                        tokens.add(new OperatorToken(start, i, new DivideOperator()));
                        break;
                    case "^":
                        tokens.add(new OperatorToken(start, i, new PowerOperator()));
                        break;
                    case "%":
                        tokens.add(new OperatorToken(start, i, new ModuloOperator()));
                        break;
                    case "..":
                        tokens.add(new OperatorToken(start, i, new ConcatenateOperator()));
                        break;
                    case "<":
                        tokens.add(new OperatorToken(start, i, new LessThanOperator()));
                        break;
                    case "<=":
                        tokens.add(new OperatorToken(start, i, new LessThanOrEqualOperator()));
                        break;
                    case ">":
                        tokens.add(new OperatorToken(start, i, new GreaterThanOperator()));
                        break;
                    case ">=":
                        tokens.add(new OperatorToken(start, i, new GreaterThanOrEqualOperator()));
                        break;
                    case "==":
                        tokens.add(new OperatorToken(start, i, new EqualOperator()));
                        break;
                    case "!=":
                        tokens.add(new OperatorToken(start, i, new UnequalOperator()));
                        break;
                    case "=":
                        tokens.add(new AssignToken(start, i));
                        break;
                    case ",":
                        tokens.add(new CommaToken(start, i));
                        break;
                    case ".":
                        tokens.add(new PeriodToken(start, i));
                        break;
                    case ";":
                        tokens.add(new SemicolonToken(start, i));
                        break;
                    case ":":
                        tokens.add(new ColonToken(start, i));
                        break;
                    case "(":
                        tokens.add(new LParenToken(start, i));
                        break;
                    case ")":
                        tokens.add(new RParenToken(start, i));
                        break;
                    case "{":
                        tokens.add(new LBraceToken(start, i));
                        break;
                    case "}":
                        tokens.add(new RBraceToken(start, i));
                        break;
                    case "[":
                        tokens.add(new LBracketToken(start, i));
                        break;
                    case "]":
                        tokens.add(new RBracketToken(start, i));
                        break;
                    default:
                        throw new Error(input, start, i, "Unknown operator: " + operator);
                }
            } else {
                throw new Error(input, i, i + 1, "Unknown character: " + input.charAt(i));
            }
        }

        return tokens;
    }
}
