package org.klobt.builtin;

import org.klobt.ArgumentList;
import org.klobt.Context;
import org.klobt.Error;
import org.klobt.ast.Node;
import org.klobt.value.*;

public class PrintBuiltin extends Builtin {
    @Override
    public Value evaluate(Context context, Node node, ArgumentList<Value> arguments) {
        String end;
        Value endValue = arguments.getKeywordArguments().get("end");
        if (endValue == null) {
            end = "\r\n";
        } else if (endValue instanceof StringValue value) {
            end = value.getValue();
        } else {
            throw new Error(context.getInput(), node.getStart(), node.getEnd(), "The end parameter must be a string");
        }

        boolean isFirst = true;

        for (Value argument : arguments.getPositionalArguments()) {
            if (!isFirst) {
                context.getWriter().write(" ");
            }

            argument.print(context.getWriter());
        }

        context.getWriter().write(end);

        return new NullValue();
    }
}
