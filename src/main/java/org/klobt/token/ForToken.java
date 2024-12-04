package org.klobt.token;

public class ForToken extends Token {
    public ForToken(int start, int end) {
        super(start, end);
    }

    @Override
    public String toString() {
        return "For";
    }
}
