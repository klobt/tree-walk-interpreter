package org.klobt.operator;

import org.klobt.Context;
import org.klobt.Error;
import org.klobt.token.Token;
import org.klobt.value.BooleanValue;
import org.klobt.value.Value;

public class AndOperator implements BinaryOperator {
    @Override
    public Value binaryOperation(Context context, Token token, Value left, Value right) {
        if (!(left instanceof BooleanValue && right instanceof BooleanValue)) {
            throw new Error(context.getInput(), token.getStart(), token.getEnd(), "Both operands of conjunction must be booleans");
        }

        boolean leftValue = ((BooleanValue) left).getValue();
        boolean rightValue = ((BooleanValue) right).getValue();

        return new BooleanValue(leftValue && rightValue);
    }

    @Override
    public String toString() {
        return "And";
    }
}
