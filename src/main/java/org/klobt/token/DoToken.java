package org.klobt.token;

public class DoToken extends Token {
    public DoToken(int start, int end) {
        super(start, end);
    }

    @Override
    public String toString() {
        return "Do";
    }
}
