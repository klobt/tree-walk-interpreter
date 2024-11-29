package org.klobt.token;

public class ThenToken extends Token {
    public ThenToken(int start, int end) {
        super(start, end);
    }

    @Override
    public String toString() {
        return "Then";
    }
}
