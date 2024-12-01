package org.klobt.operator;

import org.klobt.Context;
import org.klobt.Error;
import org.klobt.ast.LiteralNode;
import org.klobt.ast.Node;
import org.klobt.value.NumberValue;
import org.klobt.value.Value;

import java.util.Objects;

public class AddOperator implements BinaryOperator {
    @Override
    public Value evaluate(Context context, Node node, Value left, Value right) {
        if (!(left instanceof NumberValue && right instanceof NumberValue)) {
            throw new Error(context.getInput(), node.getStart(), node.getEnd(), "Both operands of addition must be numbers");
        }

        double leftValue = ((NumberValue) left).getValue();
        double rightValue = ((NumberValue) right).getValue();

        return new NumberValue(leftValue + rightValue);
    }

    @Override
    public Precedence precedence() {
        return Precedence.ADD_SUBTRACT;
    }

    @Override
    public String toString() {
        return "Add";
    }

    @Override
    public boolean equals(Object o) {
        return !(o == null || getClass() != o.getClass());
    }
}
