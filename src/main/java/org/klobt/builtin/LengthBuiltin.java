package org.klobt.builtin;

import org.klobt.ArgumentList;
import org.klobt.Context;
import org.klobt.Error;
import org.klobt.ast.Node;
import org.klobt.value.*;

public class LengthBuiltin extends Builtin {
    @Override
    public Value evaluate(Context context, Node node, ArgumentList<Value> arguments) {
        if (arguments.getPositionalArguments().isEmpty()) {
            throw new Error(context.getInput(), node.getStart(), node.getEnd(), "Not enough arguments");
        }

        Value argument = arguments.getPositionalArguments().getFirst();

        if (argument instanceof StringValue value) {
            return new NumberValue(value.getValue().length());
        } else if (argument instanceof ArrayValue value) {
            return new NumberValue(value.getValues().size());
        } else {
            throw new Error(context.getInput(), node.getStart(), node.getEnd(), "Argument must be a string or an array");
        }
    }
}
