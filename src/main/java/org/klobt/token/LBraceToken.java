package org.klobt.token;

public class LBraceToken extends Token {
    public LBraceToken(int start, int end) {
        super(start, end);
    }

    @Override
    public String toString() {
        return "LBrace";
    }
}
