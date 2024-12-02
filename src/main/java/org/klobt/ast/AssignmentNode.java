package org.klobt.ast;

import org.klobt.Context;
import org.klobt.control.BreakException;
import org.klobt.control.ContinueException;
import org.klobt.control.ReturnException;
import org.klobt.value.NullValue;
import org.klobt.value.Value;

import java.util.Objects;

public class AssignmentNode extends Node {
    private final String name;
    private final Node node;

    public AssignmentNode(int start, int end, String name, Node node) {
        super(start, end);
        this.name = name;
        this.node = node;
    }

    @Override
    public String toString() {
        return "Assignment(" + name + ", " + node + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AssignmentNode that = (AssignmentNode) o;

        return Objects.equals(name, that.name) && Objects.equals(node, that.node);
    }

    @Override
    public Value evaluate(Context context) throws BreakException, ContinueException, ReturnException {
        context.setVariable(name, node.evaluate(context));

        return new NullValue();
    }
}
