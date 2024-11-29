package org.klobt.token;

public class CommaToken extends Token {
    public CommaToken(int start, int end) {
        super(start, end);
    }

    @Override
    public String toString() {
        return "Comma";
    }
}
