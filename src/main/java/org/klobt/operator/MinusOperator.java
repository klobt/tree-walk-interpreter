package org.klobt.operator;

import org.klobt.Context;
import org.klobt.Error;
import org.klobt.token.Token;
import org.klobt.value.NumberValue;
import org.klobt.value.Value;

public class MinusOperator implements BinaryOperator, UnaryOperator {
    @Override
    public Value binaryOperation(Context context, Token token, Value left, Value right) {
        if (!(left instanceof NumberValue && right instanceof NumberValue)) {
            throw new Error(context.getInput(), token.getStart(), token.getEnd(), "Both operands of subtraction must be numbers");
        }

        double leftValue = ((NumberValue) left).getValue();
        double rightValue = ((NumberValue) right).getValue();

        return new NumberValue(leftValue - rightValue);
    }

    @Override
    public Value unaryOperation(Context context, Token token, Value argument) {
        if (!(argument instanceof NumberValue)) {
            throw new Error(context.getInput(), token.getStart(), token.getEnd(), "The operand of negation must be a number");
        }

        double value = ((NumberValue) argument).getValue();

        return new NumberValue(-value);
    }

    @Override
    public String toString() {
        return "Minus";
    }
}
