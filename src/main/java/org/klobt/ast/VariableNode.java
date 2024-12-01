package org.klobt.ast;

import org.klobt.Context;
import org.klobt.value.Value;

import java.util.Objects;

public class VariableNode extends PureNode {
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

    @Override
    public Value evaluate(Context context) {
        return context.getVariable(name);
    }
}
