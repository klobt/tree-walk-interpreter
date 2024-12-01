package org.klobt.operator;

import org.klobt.Context;
import org.klobt.Error;
import org.klobt.ast.Node;
import org.klobt.value.NumberValue;
import org.klobt.value.Value;

public class DivideOperator implements BinaryOperator {
    @Override
    public Value evaluate(Context context, Node node, Value left, Value right) {
        if (!(left instanceof NumberValue && right instanceof NumberValue)) {
            throw new Error(context.getInput(), node.getStart(), node.getEnd(), "Both operands of division must be numbers");
        }

        double leftValue = ((NumberValue) left).getValue();
        double rightValue = ((NumberValue) right).getValue();

        return new NumberValue(leftValue / rightValue);
    }

    @Override
    public Precedence precedence() {
        return Precedence.MULTIPLY_DIVIDE;
    }

    @Override
    public String toString() {
        return "Divide";
    }

    @Override
    public boolean equals(Object o) {
        return !(o == null || getClass() != o.getClass());
    }
}
