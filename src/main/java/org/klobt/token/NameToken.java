package org.klobt.token;

public class NameToken extends Token {
    protected final String value;

    public NameToken(int start, int end, String value) {
        super(start, end);
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Name(" + value + ")";
    }
}
