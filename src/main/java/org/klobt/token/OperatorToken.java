package org.klobt.token;

public class OperatorToken extends Token {
    protected Object value;

    public OperatorToken(int start, int end, Object value) {
        super(start, end);
        this.value = value;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Operator(" + value + ")";
    }
}
