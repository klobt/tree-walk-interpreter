package org.klobt.operator;

import org.klobt.Context;
import org.klobt.Error;
import org.klobt.token.Token;
import org.klobt.value.BooleanValue;
import org.klobt.value.NumberValue;
import org.klobt.value.Value;

public class GreaterThanOrEqualOperator implements BinaryOperator {
    @Override
    public Value binaryOperation(Context context, Token token, Value left, Value right) {
        if (!(left instanceof NumberValue && right instanceof NumberValue)) {
            throw new Error(context.getInput(), token.getStart(), token.getEnd(), "Both operands of comparison must be numbers");
        }

        double leftValue = ((NumberValue) left).getValue();
        double rightValue = ((NumberValue) right).getValue();

        return new BooleanValue(leftValue >= rightValue);
    }

    @Override
    public String toString() {
        return "GreaterThanOrEqual";
    }
}
