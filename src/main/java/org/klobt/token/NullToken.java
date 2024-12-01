package org.klobt.token;

public class NullToken extends Token {
    public NullToken(int start, int end) {
        super(start, end);
    }

    @Override
    public String toString() {
        return "Null";
    }
}
