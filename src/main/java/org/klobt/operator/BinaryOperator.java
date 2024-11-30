package org.klobt.operator;

import org.klobt.Context;
import org.klobt.token.Token;
import org.klobt.value.Value;

public interface BinaryOperator {
    Value binaryOperation(Context context, Token token, Value left, Value right);
    Precedence precedence();
}
