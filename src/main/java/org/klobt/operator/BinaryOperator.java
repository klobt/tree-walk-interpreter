package org.klobt.operator;

import org.klobt.Context;
import org.klobt.ast.Node;
import org.klobt.value.Value;

public interface BinaryOperator {
    Value evaluate(Context context, Node node, Value left, Value right);

    Precedence precedence();
}
