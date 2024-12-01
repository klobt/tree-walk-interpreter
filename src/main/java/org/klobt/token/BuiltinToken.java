package org.klobt.token;

import org.klobt.builtin.Builtin;

public class BuiltinToken extends Token {
    private final Builtin builtin;

    public BuiltinToken(int start, int end, Builtin builtin) {
        super(start, end);
        this.builtin = builtin;
    }

    public Builtin getBuiltin() {
        return builtin;
    }

    @Override
    public String toString() {
        return "Builtin(" + builtin + ")";
    }
}
