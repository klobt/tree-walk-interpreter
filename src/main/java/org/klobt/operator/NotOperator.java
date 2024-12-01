package org.klobt.operator;

import org.klobt.Context;
import org.klobt.Error;
import org.klobt.ast.Node;
import org.klobt.value.BooleanValue;
import org.klobt.value.Value;

public class NotOperator implements UnaryOperator {
    @Override
    public Value evaluate(Context context, Node node, Value argument) {
        if (!(argument instanceof BooleanValue)) {
            throw new Error(context.getInput(), node.getStart(), node.getEnd(), "The operand of logical negation must be a boolean");
        }

        boolean value = ((BooleanValue) argument).getValue();

        return new BooleanValue(!value);
    }

    @Override
    public String toString() {
        return "Not";
    }

    @Override
    public boolean equals(Object o) {
        return !(o == null || getClass() != o.getClass());
    }
}
