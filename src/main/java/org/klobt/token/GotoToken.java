package org.klobt.token;

public class GotoToken extends Token {
    public GotoToken(int start, int end) {
        super(start, end);
    }

    @Override
    public String toString() {
        return "Goto";
    }
}
