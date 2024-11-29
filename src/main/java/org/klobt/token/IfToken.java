package org.klobt.token;

public class IfToken extends Token {
    public IfToken(int start, int end) {
        super(start, end);
    }

    @Override
    public String toString() {
        return "If";
    }
}
