package org.klobt.ast;

import org.klobt.Context;
import org.klobt.control.BreakException;
import org.klobt.value.Value;

import java.util.Objects;

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
        throw new BreakException();
    }
}
