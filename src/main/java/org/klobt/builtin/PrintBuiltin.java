package org.klobt.builtin;

import org.klobt.ArgumentList;
import org.klobt.Context;
import org.klobt.Error;
import org.klobt.ast.Node;
import org.klobt.value.*;

import java.util.HashMap;
import java.util.List;

public class PrintBuiltin extends Builtin {
    @Override
    public Value evaluate(Context context, Node node, ArgumentList<Value> arguments) {
        if (!arguments.getKeywordArguments().isEmpty()) {
            throw new Error(context.getInput(), node.getStart(), node.getEnd(), "No keyword arguments allowed");
        }

        for (Value argument : arguments.getPositionalArguments()) {
            if (argument instanceof BooleanValue value) {
                System.out.println(value.getValue());
            } else if (argument instanceof NullValue) {
                System.out.println("null");
            } else if (argument instanceof NumberValue value) {
                System.out.println(value.getValue());
            } else if (argument instanceof StringValue value) {
                System.out.println(value.getValue());
            } else if (argument instanceof BuiltinValue value) {
                System.out.println("<builtin " + value.getBuiltin() + ">");
            }
        }

        return new NullValue();
    }
}
