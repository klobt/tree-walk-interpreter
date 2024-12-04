package org.klobt.ast;

import org.klobt.Context;
import org.klobt.Error;
import org.klobt.control.ContinueException;
import org.klobt.value.Value;

public class ContinueNode extends Node {
    public ContinueNode(int start, int end) {
        super(start, end);
    }

    @Override
    public String toString() {
        return "Continue";
    }

    @Override
    public boolean equals(Object o) {
        return o != null && getClass() == o.getClass();
    }

    @Override
    public Value evaluate(Context context) throws ContinueException {
        if (context.getLoopDepth() > 0) {
            throw new ContinueException();
        } else {
            throw new Error(context.getInput(), getStart(), getEnd(), "Can't call continue outside a loop");
        }
    }
}
