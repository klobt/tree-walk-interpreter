package org.klobt.token;

public class RBracketToken extends Token {
    public RBracketToken(int start, int end) {
        super(start, end);
    }

    @Override
    public String toString() {
        return "RBracket";
    }
}
