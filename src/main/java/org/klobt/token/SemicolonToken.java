package org.klobt.token;

public class SemicolonToken extends Token {
    public SemicolonToken(int start, int end) {
        super(start, end);
    }

    @Override
    public String toString() {
        return "Semicolon";
    }
}
