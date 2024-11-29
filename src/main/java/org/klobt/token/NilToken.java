package org.klobt.token;

public class NilToken extends Token {
    public NilToken(int start, int end) {
        super(start, end);
    }

    @Override
    public String toString() {
        return "Nil";
    }
}
