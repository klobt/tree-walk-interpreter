package org.klobt.token;

public class ReturnToken extends Token {
    public ReturnToken(int start, int end) {
        super(start, end);
    }

    @Override
    public String toString() {
        return "Return";
    }
}
