package org.klobt.builtin;

import org.klobt.ArgumentList;
import org.klobt.Context;
import org.klobt.Error;
import org.klobt.ast.Node;
import org.klobt.value.ArrayValue;
import org.klobt.value.NullValue;
import org.klobt.value.Value;

public class PopBuiltin extends Builtin {
    @Override
    public Value evaluate(Context context, Node node, ArgumentList<Value> arguments) {
        if (arguments.getPositionalArguments().isEmpty()) {
            throw new Error(context.getInput(), node.getStart(), node.getEnd(), "Not enough arguments");
        }

        Value array = arguments.getPositionalArguments().getFirst();

        if (array instanceof ArrayValue arrayValue) {
            arrayValue.getValues().removeLast();
        } else {
            throw new Error(context.getInput(), node.getStart(), node.getEnd(), "The first argument must be an array");
        }

        return new NullValue();
    }
}
