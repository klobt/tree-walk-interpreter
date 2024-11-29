package org.klobt.token;

public class AssignToken extends Token {
    public AssignToken(int start, int end) {
        super(start, end);
    }

    @Override
    public String toString() {
        return "Assign";
    }
}
