package org.klobt.operator;

import org.klobt.Context;
import org.klobt.Error;
import org.klobt.ast.Node;
import org.klobt.value.BooleanValue;
import org.klobt.value.NumberValue;
import org.klobt.value.Value;

public class LessThanOperator implements BinaryOperator {
    @Override
    public Value binaryOperation(Context context, Node node, Value left, Value right) {
        if (!(left instanceof NumberValue && right instanceof NumberValue)) {
            throw new Error(context.getInput(), node.getStart(), node.getEnd(), "Both operands of comparison must be numbers");
        }

        double leftValue = ((NumberValue) left).getValue();
        double rightValue = ((NumberValue) right).getValue();

        return new BooleanValue(leftValue < rightValue);
    }

    @Override
    public Precedence precedence() {
        return Precedence.COMPARISON;
    }

    @Override
    public String toString() {
        return "LessThan";
    }

    @Override
    public boolean equals(Object o) {
        return !(o == null || getClass() != o.getClass());
    }
}
