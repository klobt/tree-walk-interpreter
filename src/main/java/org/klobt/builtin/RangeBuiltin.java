package org.klobt.builtin;

import org.klobt.ArgumentList;
import org.klobt.Context;
import org.klobt.Error;
import org.klobt.ast.Node;
import org.klobt.value.ArrayValue;
import org.klobt.value.NullValue;
import org.klobt.value.NumberValue;
import org.klobt.value.Value;

import java.util.ArrayList;
import java.util.List;

public class RangeBuiltin extends Builtin {
    @Override
    public Value evaluate(Context context, Node node, ArgumentList<Value> arguments) {
        if (arguments.getPositionalArguments().size() < 2) {
            throw new Error(context.getInput(), node.getStart(), node.getEnd(), "Not enough arguments");
        }

        int start, end, delta = 1;

        Value startArgument = arguments.getPositionalArguments().get(0);
        if (startArgument instanceof NumberValue startValue) {
            start = (int) startValue.getValue();
        } else {
            throw new Error(context.getInput(), node.getStart(), node.getEnd(), "Arguments must be numbers");
        }

        Value endArgument = arguments.getPositionalArguments().get(1);
        if (endArgument instanceof NumberValue endValue) {
            end = (int) endValue.getValue();
        } else {
            throw new Error(context.getInput(), node.getStart(), node.getEnd(), "Arguments must be numbers");
        }

        if (arguments.getPositionalArguments().size() > 2) {
            Value deltaArgument = arguments.getPositionalArguments().get(1);
            if (deltaArgument instanceof NumberValue deltaValue) {
                end = (int) deltaValue.getValue();
            } else {
                throw new Error(context.getInput(), node.getStart(), node.getEnd(), "Arguments must be numbers");
            }
        }

        List<Value> values = new ArrayList<>();

        for (int i = start; i < end; i += delta) {
            values.add(new NumberValue(i));
        }

        return new ArrayValue(values);
    }
}
