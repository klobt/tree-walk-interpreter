package org.klobt.token;

public class BreakToken extends Token {
    public BreakToken(int start, int end) {
        super(start, end);
    }

    @Override
    public String toString() {
        return "Break";
    }
}
