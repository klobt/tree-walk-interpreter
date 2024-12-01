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

        boolean isFirst = true;

        for (Value argument : arguments.getPositionalArguments()) {
            if (!isFirst) {
                context.getWriter().write(" ");
            }

            if (argument instanceof BooleanValue value) {
                context.getWriter().write(value.getValue() ? "true" : "false");
            } else if (argument instanceof NullValue) {
                context.getWriter().write("null");
            } else if (argument instanceof NumberValue value) {
                context.getWriter().write(String.valueOf(value.getValue()));
            } else if (argument instanceof StringValue value) {
                context.getWriter().write(value.getValue());
            } else if (argument instanceof BuiltinValue value) {
                context.getWriter().write("<builtin " + value.getBuiltin() + ">");
            } else {
                throw new Error(context.getInput(), node.getStart(), node.getEnd(), "Unexpected value: " + argument);
            }
        }

        context.getWriter().write("\r\n");

        return new NullValue();
    }
}
