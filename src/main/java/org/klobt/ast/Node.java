package org.klobt.ast;

import org.klobt.Context;
import org.klobt.control.BreakException;
import org.klobt.control.ContinueException;
import org.klobt.value.Value;

public abstract class Node {
    private int start, end;

    public Node(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public abstract Value evaluate(Context context) throws BreakException, ContinueException;

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
