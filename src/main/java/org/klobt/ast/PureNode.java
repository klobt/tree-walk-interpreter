package org.klobt.ast;

import org.klobt.Context;
import org.klobt.control.BreakException;
import org.klobt.control.ContinueException;
import org.klobt.value.Value;

public abstract class PureNode extends Node {
    public PureNode(int start, int end) {
        super(start, end);
    }

    public abstract Value evaluate(Context context);
}
