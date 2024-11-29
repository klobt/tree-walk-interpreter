package org.klobt.token;

public class NewlineToken extends Token {
    public NewlineToken(int start, int end) {
        super(start, end);
    }

    @Override
    public String toString() {
        return "Newline";
    }
}
