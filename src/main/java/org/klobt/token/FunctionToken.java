package org.klobt.token;

public class FunctionToken extends Token {
    public FunctionToken(int start, int end) {
        super(start, end);
    }

    @Override
    public String toString() {
        return "Function";
    }
}
