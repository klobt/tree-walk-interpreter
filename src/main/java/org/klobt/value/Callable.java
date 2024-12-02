package org.klobt.value;

import org.klobt.ArgumentList;
import org.klobt.Context;
import org.klobt.ast.Node;
import org.klobt.control.BreakException;
import org.klobt.control.ContinueException;

public interface Callable {
    Value call(Context context, Node node, ArgumentList<Value> arguments);

}
