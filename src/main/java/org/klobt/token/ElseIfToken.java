package org.klobt.token;

public class ElseIfToken extends Token {
    public ElseIfToken(int start, int end) {
        super(start, end);
    }

    @Override
    public String toString() {
        return "ElseIf";
    }
}
