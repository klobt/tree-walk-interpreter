package org.klobt.token;

public class ColonToken extends Token {
    public ColonToken(int start, int end) {
        super(start, end);
    }

    @Override
    public String toString() {
        return "Colon";
    }
}
