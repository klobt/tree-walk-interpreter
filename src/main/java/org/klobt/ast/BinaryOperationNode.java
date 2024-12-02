package org.klobt.ast;

import org.klobt.Context;
import org.klobt.operator.BinaryOperator;
import org.klobt.value.Value;

import java.util.Objects;

public class BinaryOperationNode extends PureNode {
    private final PureNode leftNode, rightNode;
    private final BinaryOperator operator;

    public BinaryOperationNode(int start, int end, PureNode leftNode, PureNode rightNode, BinaryOperator operator) {
        super(start, end);
        this.leftNode = leftNode;
        this.rightNode = rightNode;
        this.operator = operator;
    }

    @Override
    public String toString() {
        return "BinaryOperation(" + leftNode + ", " + operator + ", " + rightNode + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BinaryOperationNode that = (BinaryOperationNode) o;

        return Objects.equals(leftNode, that.leftNode) && Objects.equals(rightNode, that.rightNode) && Objects.equals(operator, that.operator);
    }

    @Override
    public Value evaluate(Context context) {
        return operator.evaluate(context, this, leftNode.evaluate(context), rightNode.evaluate(context));
    }
}
