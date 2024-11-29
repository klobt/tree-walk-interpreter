package org.klobt.operator;

import org.klobt.Context;
import org.klobt.Error;
import org.klobt.table.Table;
import org.klobt.token.Token;
import org.klobt.value.*;

public class LengthOperator implements UnaryOperator {
    @Override
    public Value unaryOperation(Context context, Token token, Value argument) {
        if (argument instanceof StringValue value) {
            return new NumberValue(value.getValue().length());
        } else if (argument instanceof TableValue value) {
            return new NumberValue(context.getTableStore().getTable(value.getId()).getArraySize());
        } else {
            throw new Error(context.getInput(), token.getStart(), token.getEnd(), "The operand of length operation must be a string or a table");
        }
    }

    @Override
    public String toString() {
        return "Length";
    }
}
