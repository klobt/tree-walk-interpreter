package org.klobt.token;

public class LocalToken extends Token {
    public LocalToken(int start, int end) {
        super(start, end);
    }

    @Override
    public String toString() {
        return "Local";
    }
}
