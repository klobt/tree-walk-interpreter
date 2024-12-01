package org.klobt.token;

public class ContinueToken extends Token {
    public ContinueToken(int start, int end) {
        super(start, end);
    }

    @Override
    public String toString() {
        return "Continue";
    }
}
