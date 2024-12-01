package org.klobt.operator;

import org.klobt.Context;
import org.klobt.ast.Node;
import org.klobt.value.BooleanValue;
import org.klobt.value.Value;

public class EqualOperator implements BinaryOperator {
    @Override
    public Value evaluate(Context context, Node node, Value left, Value right) {
        return new BooleanValue(left.equals(right));
    }

    @Override
    public Precedence precedence() {
        return Precedence.COMPARISON;
    }

    @Override
    public String toString() {
        return "Equal";
    }

    @Override
    public boolean equals(Object o) {
        return !(o == null || getClass() != o.getClass());
    }
}
