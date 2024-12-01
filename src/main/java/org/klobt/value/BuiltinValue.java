package org.klobt.value;

import org.klobt.ArgumentList;
import org.klobt.Context;
import org.klobt.ast.Node;
import org.klobt.builtin.Builtin;

import java.util.Objects;

public class BuiltinValue extends Value implements Callable {
    private final Builtin builtin;

    public BuiltinValue(Builtin builtin) {
        this.builtin = builtin;
    }

    public Builtin getBuiltin() {
        return builtin;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BuiltinValue that = (BuiltinValue) o;

        return Objects.equals(builtin, that.builtin);
    }

    @Override
    public String toString() {
        return "Builtin(" + builtin + ")";
    }

    @Override
    public Value call(Context context, Node node, ArgumentList<Value> arguments) {
        return builtin.evaluate(context, node, arguments);
    }
}
