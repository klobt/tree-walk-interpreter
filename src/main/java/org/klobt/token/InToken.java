package org.klobt.token;

public class InToken extends Token {
    public InToken(int start, int end) {
        super(start, end);
    }

    @Override
    public String toString() {
        return "In";
    }
}
