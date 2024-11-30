package org.klobt.operator;

import org.klobt.Context;
import org.klobt.Error;
import org.klobt.token.Token;
import org.klobt.value.NumberValue;
import org.klobt.value.Value;

public class PowerOperator implements BinaryOperator {
    @Override
    public Value binaryOperation(Context context, Token token, Value left, Value right) {
        if (!(left instanceof NumberValue && right instanceof NumberValue)) {
            throw new Error(context.getInput(), token.getStart(), token.getEnd(), "Both operands of power must be numbers");
        }

        double leftValue = ((NumberValue) left).getValue();
        double rightValue = ((NumberValue) right).getValue();

        return new NumberValue(Math.pow(leftValue, rightValue));
    }

    @Override
    public Precedence precedence() {
        return Precedence.POWER;
    }

    @Override
    public String toString() {
        return "Power";
    }

    @Override
    public boolean equals(Object o) {
        return !(o == null || getClass() != o.getClass());
    }
}
