package org.klobt.token;

public abstract class Token {
    private final int start, end;
    private boolean isBeforeNewline;

    public Token(int start, int end) {
        this.start = start;
        this.end = end;
        isBeforeNewline = false;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public void setBeforeNewline(boolean beforeNewline) {
        isBeforeNewline = beforeNewline;
    }

    public boolean isBeforeNewline() {
        return isBeforeNewline;
    }
}
