package org.klobt.value;

import org.klobt.ArgumentList;
import org.klobt.Context;
import org.klobt.ast.Node;
import org.klobt.io.Writer;

import java.util.*;

public class MethodValue extends Value implements Callable {
    private final ObjectValue object;
    private final FunctionValue function;

    public MethodValue(ObjectValue object, FunctionValue function) {
        this.object = object;
        this.function = function;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        MethodValue that = (MethodValue) o;
        return Objects.equals(object, that.object) && Objects.equals(function, that.function);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), object, function);
    }

    @Override
    public void print(Writer writer) {
        writer.write("<method>");
    }

    @Override
    public String toString() {
        return "Method(" + object + ", " + function + ")";
    }

    @Override
    public Value call(Context context, Node node, ArgumentList<Value> arguments) {
        List<Value> positionalArguments = new ArrayList<>();

        positionalArguments.add(object);

        positionalArguments.addAll(arguments.getPositionalArguments());


        return function.call(context, node, new ArgumentList<>(positionalArguments, arguments.getKeywordArguments()));
    }
}
