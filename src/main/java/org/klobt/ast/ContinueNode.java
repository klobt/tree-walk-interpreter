package org.klobt.ast;

import org.klobt.Context;
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
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        return true;
    }

    @Override
    public Value evaluate(Context context) throws ContinueException {
        throw new ContinueException();
    }
}
