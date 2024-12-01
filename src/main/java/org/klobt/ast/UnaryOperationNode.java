package org.klobt.ast;

import org.klobt.Context;
import org.klobt.operator.UnaryOperator;
import org.klobt.value.Value;

import java.util.Objects;

public class UnaryOperationNode extends PureNode {
    private final PureNode node;
    private final UnaryOperator operator;

    public UnaryOperationNode(int start, int end, PureNode node, UnaryOperator operator) {
        super(start, end);
        this.node = node;
        this.operator = operator;
    }

    @Override
    public String toString() {
        return "UnaryOperation(" + operator + ", " + node + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UnaryOperationNode that = (UnaryOperationNode) o;

        return Objects.equals(node, that.node) && Objects.equals(operator, that.operator);
    }

    @Override
    public Value evaluate(Context context) {
        return operator.evaluate(context, this, node.evaluate(context));
    }
}
