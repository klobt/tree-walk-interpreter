package org.klobt.token;

public class ElseToken extends Token {
    public ElseToken(int start, int end) {
        super(start, end);
    }

    @Override
    public String toString() {
        return "Else";
    }
}
