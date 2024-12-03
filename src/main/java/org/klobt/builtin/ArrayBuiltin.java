package org.klobt.builtin;

import org.klobt.ArgumentList;
import org.klobt.Context;
import org.klobt.ast.Node;
import org.klobt.value.ArrayValue;
import org.klobt.value.Value;

public class ArrayBuiltin extends Builtin {
    @Override
    public Value evaluate(Context context, Node node, ArgumentList<Value> arguments) {
        return new ArrayValue(arguments.getPositionalArguments());
    }
}
