package org.klobt;

import org.junit.jupiter.api.Test;
import org.klobt.operator.*;
import org.klobt.token.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TokenizerTest {

    private final Tokenizer tokenizer = new Tokenizer();

    @Test
    public void testTokenizeNumbers() {
        List<Token> tokens = tokenizer.tokenize("123 45.67 8e2");
        assertEquals(3, tokens.size());
        assertTrue(tokens.get(0) instanceof NumberToken);
        assertEquals(123.0, ((NumberToken) tokens.get(0)).getValue());
        assertTrue(tokens.get(1) instanceof NumberToken);
        assertEquals(45.67, ((NumberToken) tokens.get(1)).getValue());
        assertTrue(tokens.get(2) instanceof NumberToken);
        assertEquals(800.0, ((NumberToken) tokens.get(2)).getValue());
    }

    @Test
    public void testTokenizeStrings() {
        List<Token> tokens = tokenizer.tokenize("\"hello\" 'world'");
        assertEquals(2, tokens.size());
        assertTrue(tokens.get(0) instanceof StringToken);
        assertEquals("hello", ((StringToken) tokens.get(0)).getValue());
        assertTrue(tokens.get(1) instanceof StringToken);
        assertEquals("world", ((StringToken) tokens.get(1)).getValue());
    }

    @Test
    public void testTokenizeSpecialCharacters() {
        List<Token> tokens = tokenizer.tokenize("+ - * / ^ %");
        assertEquals(6, tokens.size());
        assertTrue(tokens.get(0) instanceof OperatorToken);
        assertTrue(((OperatorToken) tokens.get(0)).getValue() instanceof AddOperator);
        assertTrue(((OperatorToken) tokens.get(1)).getValue() instanceof MinusOperator);
        assertTrue(((OperatorToken) tokens.get(2)).getValue() instanceof MultiplyOperator);
        assertTrue(((OperatorToken) tokens.get(3)).getValue() instanceof DivideOperator);
        assertTrue(((OperatorToken) tokens.get(4)).getValue() instanceof PowerOperator);
        assertTrue(((OperatorToken) tokens.get(5)).getValue() instanceof ModuloOperator);
    }

    @Test
    public void testTokenizeComments() {
        List<Token> tokens = tokenizer.tokenize("-- Single line comment\n123");
        assertEquals(2, tokens.size());
        assertTrue(tokens.get(1) instanceof NumberToken);
        assertEquals(123.0, ((NumberToken) tokens.get(1)).getValue());
    }

    @Test
    public void testTokenizeLongComments() {
        List<Token> tokens = tokenizer.tokenize("--[[ Multi-line\ncomment ]]123");
        assertEquals(1, tokens.size());
        assertTrue(tokens.get(0) instanceof NumberToken);
        assertEquals(123.0, ((NumberToken) tokens.get(0)).getValue());
    }

    @Test
    public void testTokenizeKeywords() {
        List<Token> tokens = tokenizer.tokenize("if else true false nil");
        assertEquals(5, tokens.size());
        assertTrue(tokens.get(0) instanceof IfToken);
        assertTrue(tokens.get(1) instanceof ElseToken);
        assertTrue(tokens.get(2) instanceof BooleanToken);
        assertEquals(true, ((BooleanToken) tokens.get(2)).getValue());
        assertTrue(tokens.get(3) instanceof BooleanToken);
        assertEquals(false, ((BooleanToken) tokens.get(3)).getValue());
        assertTrue(tokens.get(4) instanceof NilToken);
    }

    @Test
    public void testTokenizeName() {
        List<Token> tokens = tokenizer.tokenize("variable_name");
        assertEquals(1, tokens.size());
        assertTrue(tokens.get(0) instanceof NameToken);
        assertEquals("variable_name", ((NameToken) tokens.get(0)).getValue());
    }

    @Test
    public void testTokenizeOperators() {
        List<Token> tokens = tokenizer.tokenize("== ~= <= >= < >");
        assertEquals(6, tokens.size());
        assertTrue(tokens.get(0) instanceof OperatorToken);
        assertTrue(((OperatorToken) tokens.get(0)).getValue() instanceof EqualOperator);
        assertTrue(((OperatorToken) tokens.get(1)).getValue() instanceof UnequalOperator);
        assertTrue(((OperatorToken) tokens.get(2)).getValue() instanceof LessThanOrEqualOperator);
        assertTrue(((OperatorToken) tokens.get(3)).getValue() instanceof GreaterThanOrEqualOperator);
        assertTrue(((OperatorToken) tokens.get(4)).getValue() instanceof LessThanOperator);
        assertTrue(((OperatorToken) tokens.get(5)).getValue() instanceof GreaterThanOperator);
    }

    @Test
    public void testTokenizeErrorUnterminatedString() {
        Error error = assertThrows(Error.class, () -> tokenizer.tokenize("\"unterminated string"));
        assertTrue(error.getMessage().contains("Unterminated string literal"));
    }

    @Test
    public void testTokenizeErrorUnknownCharacter() {
        Error error = assertThrows(Error.class, () -> tokenizer.tokenize("unknown@character"));
        assertTrue(error.getMessage().contains("Unknown operator: @"));
    }

    @Test
    public void testTokenizeErrorExpectedClosingBracket() {
        Error error = assertThrows(Error.class, () -> tokenizer.tokenize("[[unterminated"));
        assertTrue(error.getMessage().contains("Expected ]]"));
    }
}
