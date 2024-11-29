package org.klobt.token;

public class UntilToken extends Token {
    public UntilToken(int start, int end) {
        super(start, end);
    }

    @Override
    public String toString() {
        return "Until";
    }
}
