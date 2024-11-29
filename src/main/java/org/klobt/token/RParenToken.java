package org.klobt.token;

public class RParenToken extends Token {
    public RParenToken(int start, int end) {
        super(start, end);
    }

    @Override
    public String toString() {
        return "RParen";
    }
}
