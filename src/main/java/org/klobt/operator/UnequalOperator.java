package org.klobt.operator;

import org.klobt.Context;
import org.klobt.token.Token;
import org.klobt.value.BooleanValue;
import org.klobt.value.Value;

public class UnequalOperator implements BinaryOperator {
    @Override
    public Value binaryOperation(Context context, Token token, Value left, Value right) {
        return new BooleanValue(!left.equals(right));
    }

    @Override
    public String toString() {
        return "Unequal";
    }
}
