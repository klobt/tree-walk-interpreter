package org.klobt.token;

public class LParenToken extends Token {
    public LParenToken(int start, int end) {
        super(start, end);
    }

    @Override
    public String toString() {
        return "LParen";
    }
}
