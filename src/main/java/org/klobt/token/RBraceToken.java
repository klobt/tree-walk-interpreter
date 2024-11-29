package org.klobt.token;

public class RBraceToken extends Token {
    public RBraceToken(int start, int end) {
        super(start, end);
    }

    @Override
    public String toString() {
        return "RBrace";
    }
}
