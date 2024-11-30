package org.klobt.operator;

import org.klobt.Context;
import org.klobt.Error;
import org.klobt.token.Token;
import org.klobt.value.StringValue;
import org.klobt.value.Value;

public class ConcatenateOperator implements BinaryOperator {
    @Override
    public Value binaryOperation(Context context, Token token, Value left, Value right) {
        if (!(left instanceof StringValue && right instanceof StringValue)) {
            throw new Error(context.getInput(), token.getStart(), token.getEnd(), "Both operands of concatenation must be strings");
        }

        String leftValue = ((StringValue) left).getValue();
        String rightValue = ((StringValue) right).getValue();

        return new StringValue(leftValue + rightValue);
    }

    @Override
    public Precedence precedence() {
        return Precedence.CONCATENATE;
    }

    @Override
    public String toString() {
        return "Concatenate";
    }

    @Override
    public boolean equals(Object o) {
        return !(o == null || getClass() != o.getClass());
    }
}
