package org.klobt.operator;

import org.klobt.Context;
import org.klobt.ast.Node;
import org.klobt.value.Value;

public interface UnaryOperator {
    Value evaluate(Context context, Node node, Value argument);
}
