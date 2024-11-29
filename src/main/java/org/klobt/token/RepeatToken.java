package org.klobt.token;

public class RepeatToken extends Token {
    public RepeatToken(int start, int end) {
        super(start, end);
    }

    @Override
    public String toString() {
        return "Repeat";
    }
}
