package org.klobt.operator;

import org.klobt.Context;
import org.klobt.Error;
import org.klobt.ast.Node;
import org.klobt.value.BooleanValue;
import org.klobt.value.Value;

public class AndOperator implements BinaryOperator {
    @Override
    public Value evaluate(Context context, Node node, Value left, Value right) {
        if (!(left instanceof BooleanValue && right instanceof BooleanValue)) {
            throw new Error(context.getInput(), node.getStart(), node.getEnd(), "Both operands of conjunction must be booleans");
        }

        boolean leftValue = ((BooleanValue) left).getValue();
        boolean rightValue = ((BooleanValue) right).getValue();

        return new BooleanValue(leftValue && rightValue);
    }

    @Override
    public Precedence precedence() {
        return Precedence.AND;
    }

    @Override
    public String toString() {
        return "And";
    }

    @Override
    public boolean equals(Object o) {
        return !(o == null || getClass() != o.getClass());
    }
}
