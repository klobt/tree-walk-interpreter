package org.klobt.ast;

import org.klobt.value.Value;

import java.util.Objects;

public class LiteralNode extends Node {
    private final Value value;

    public LiteralNode(int start, int end, Value value) {
        super(start, end);
        this.value = value;
    }

    @Override
    public String toString() {
        return "Literal(" + value + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LiteralNode that = (LiteralNode) o;

        return Objects.equals(value, that.value);
    }
}
