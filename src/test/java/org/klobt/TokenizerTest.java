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
        assertInstanceOf(NumberToken.class, tokens.get(0));
        assertEquals(123.0, ((NumberToken) tokens.get(0)).getValue());
        assertInstanceOf(NumberToken.class, tokens.get(1));
        assertEquals(45.67, ((NumberToken) tokens.get(1)).getValue());
        assertInstanceOf(NumberToken.class, tokens.get(2));
        assertEquals(800.0, ((NumberToken) tokens.get(2)).getValue());
    }

    @Test
    public void testTokenizeStrings() {
        List<Token> tokens = tokenizer.tokenize("\"hello\" 'world'");
        assertEquals(2, tokens.size());
        assertInstanceOf(StringToken.class, tokens.get(0));
        assertEquals("hello", ((StringToken) tokens.get(0)).getValue());
        assertInstanceOf(StringToken.class, tokens.get(1));
        assertEquals("world", ((StringToken) tokens.get(1)).getValue());
    }

    @Test
    public void testTokenizeSpecialCharacters() {
        List<Token> tokens = tokenizer.tokenize("+ - * / ^ %");
        assertEquals(6, tokens.size());
        assertInstanceOf(OperatorToken.class, tokens.get(0));
        assertInstanceOf(AddOperator.class, ((OperatorToken) tokens.get(0)).getValue());
        assertInstanceOf(MinusOperator.class, ((OperatorToken) tokens.get(1)).getValue());
        assertInstanceOf(MultiplyOperator.class, ((OperatorToken) tokens.get(2)).getValue());
        assertInstanceOf(DivideOperator.class, ((OperatorToken) tokens.get(3)).getValue());
        assertInstanceOf(PowerOperator.class, ((OperatorToken) tokens.get(4)).getValue());
        assertInstanceOf(ModuloOperator.class, ((OperatorToken) tokens.get(5)).getValue());
    }

    @Test
    public void testTokenizeComments() {
        List<Token> tokens = tokenizer.tokenize("-- Single line comment\n123");
        assertEquals(2, tokens.size());
        assertInstanceOf(NumberToken.class, tokens.get(1));
        assertEquals(123.0, ((NumberToken) tokens.get(1)).getValue());
    }

    @Test
    public void testTokenizeLongComments() {
        List<Token> tokens = tokenizer.tokenize("--[[ Multi-line\ncomment ]]123");
        assertEquals(1, tokens.size());
        assertInstanceOf(NumberToken.class, tokens.getFirst());
        assertEquals(123.0, ((NumberToken) tokens.getFirst()).getValue());
    }

    @Test
    public void testTokenizeName() {
        List<Token> tokens = tokenizer.tokenize("variable_name");
        assertEquals(1, tokens.size());
        assertInstanceOf(NameToken.class, tokens.getFirst());
        assertEquals("variable_name", ((NameToken) tokens.getFirst()).getValue());
    }

    @Test
    public void testTokenizeOperators() {
        List<Token> tokens = tokenizer.tokenize("== != <= >= < >");
        assertEquals(6, tokens.size());
        assertInstanceOf(OperatorToken.class, tokens.get(0));
        assertInstanceOf(EqualOperator.class, ((OperatorToken) tokens.get(0)).getValue());
        assertInstanceOf(UnequalOperator.class, ((OperatorToken) tokens.get(1)).getValue());
        assertInstanceOf(LessThanOrEqualOperator.class, ((OperatorToken) tokens.get(2)).getValue());
        assertInstanceOf(GreaterThanOrEqualOperator.class, ((OperatorToken) tokens.get(3)).getValue());
        assertInstanceOf(LessThanOperator.class, ((OperatorToken) tokens.get(4)).getValue());
        assertInstanceOf(GreaterThanOperator.class, ((OperatorToken) tokens.get(5)).getValue());
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
