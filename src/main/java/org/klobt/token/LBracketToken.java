package org.klobt.token;

public class LBracketToken extends Token {
    public LBracketToken(int start, int end) {
        super(start, end);
    }

    @Override
    public String toString() {
        return "LBracket";
    }
}
