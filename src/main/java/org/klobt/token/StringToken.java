package org.klobt.token;

public class StringToken extends Token {
    protected final String value;

    public StringToken(int start, int end, String value) {
        super(start, end);
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "String(" + value + ")";
    }
}
