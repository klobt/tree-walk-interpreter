package org.klobt.token;

public class NumberToken extends Token {
    protected final double value;

    public NumberToken(int start, int end, double value) {
        super(start, end);
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Number(" + value + ")";
    }
}
