package org.klobt.ast;

import java.util.Objects;

public class VariableNode extends Node {
    private final String name;

    public VariableNode(int start, int end, String name) {
        super(start, end);
        this.name = name;
    }

    @Override
    public String toString() {
        return "Variable(" + name + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        VariableNode that = (VariableNode) o;

        return Objects.equals(name, that.name);
    }
}