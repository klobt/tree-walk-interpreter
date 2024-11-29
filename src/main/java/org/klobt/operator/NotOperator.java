package org.klobt.operator;

import org.klobt.Context;
import org.klobt.Error;
import org.klobt.token.Token;
import org.klobt.value.BooleanValue;
import org.klobt.value.Value;

public class NotOperator implements UnaryOperator {
    @Override
    public Value unaryOperation(Context context, Token token, Value argument) {
        if (!(argument instanceof BooleanValue)) {
            throw new Error(context.getInput(), token.getStart(), token.getEnd(), "The operand of logical negation must be a boolean");
        }

        boolean value = ((BooleanValue) argument).getValue();

        return new BooleanValue(!value);
    }

    @Override
    public String toString() {
        return "Not";
    }
}
