package org.klobt.token;

public class PeriodToken extends Token {
    public PeriodToken(int start, int end) {
        super(start, end);
    }

    @Override
    public String toString() {
        return "Period";
    }
}
