package org.klobt.token;

public class BooleanToken extends Token {
    protected final boolean value;

    public BooleanToken(int start, int end, boolean value) {
        super(start, end);
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Boolean(" + value + ")";
    }
}
