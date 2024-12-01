package org.klobt.builtin;

import org.klobt.ArgumentList;
import org.klobt.Context;
import org.klobt.ast.Node;
import org.klobt.value.Value;

import java.util.HashMap;
import java.util.List;

public abstract class Builtin {
    public abstract Value evaluate(Context context, Node node, ArgumentList<Value> arguments);
}
