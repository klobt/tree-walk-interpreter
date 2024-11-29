package org.klobt.operator;

import org.klobt.Context;
import org.klobt.token.Token;
import org.klobt.value.Value;

public interface UnaryOperator {
    Value unaryOperation(Context context, Token token, Value argument);
}
