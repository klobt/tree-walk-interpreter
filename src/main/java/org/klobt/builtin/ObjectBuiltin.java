package org.klobt.builtin;

import org.klobt.ArgumentList;
import org.klobt.Context;
import org.klobt.ast.Node;
import org.klobt.value.ObjectValue;
import org.klobt.value.Value;

public class ObjectBuiltin extends Builtin {
    @Override
    public Value evaluate(Context context, Node node, ArgumentList<Value> arguments) {
        return new ObjectValue(arguments.getKeywordArguments());
    }
}
