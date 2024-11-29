package org.klobt.token;

public class EndToken extends Token {
    public EndToken(int start, int end) {
        super(start, end);
    }

    @Override
    public String toString() {
        return "End";
    }
}
