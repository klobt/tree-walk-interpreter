package org.klobt.token;

public class WhileToken extends Token {
    public WhileToken(int start, int end) {
        super(start, end);
    }

    @Override
    public String toString() {
        return "While";
    }
}
