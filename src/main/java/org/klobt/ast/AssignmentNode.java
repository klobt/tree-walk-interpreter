package org.klobt.ast;

import org.klobt.Context;
import org.klobt.control.BreakException;
import org.klobt.control.ContinueException;
import org.klobt.control.ReturnException;
import org.klobt.value.NullValue;
import org.klobt.value.Value;

import java.util.Objects;

public class AssignmentNode extends Node {
    private final LHSNode lhs;
    private final Node node;

    public AssignmentNode(int start, int end, LHSNode lhs, Node node) {
        super(start, end);
        this.lhs = lhs;
        this.node = node;
    }

    @Override
    public String toString() {
        return "Assignment(" + lhs + ", " + node + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AssignmentNode that = (AssignmentNode) o;

        return Objects.equals(lhs, that.lhs) && Objects.equals(node, that.node);
    }

    @Override
    public Value evaluate(Context context) throws BreakException, ContinueException, ReturnException {
        lhs.assign(context, node.evaluate(context));

        return new NullValue();
    }
}
