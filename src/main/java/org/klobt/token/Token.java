package org.klobt.token;

public abstract class Token {
    private final int start, end;

    public Token(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }
}
