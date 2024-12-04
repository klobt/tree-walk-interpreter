package org.klobt.ast;

import org.klobt.Context;
import org.klobt.Error;
import org.klobt.control.BreakException;
import org.klobt.value.Value;

public class BreakNode extends Node {
    public BreakNode(int start, int end) {
        super(start, end);
    }

    @Override
    public String toString() {
        return "Break";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        return true;
    }

    @Override
    public Value evaluate(Context context) throws BreakException {
        if (context.getLoopDepth() > 0) {
            throw new BreakException();
        } else {
            throw new Error(context.getInput(), getStart(), getEnd(), "Can't call break outside a loop");
        }
    }
}
