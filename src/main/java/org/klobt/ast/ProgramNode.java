package org.klobt.ast;

import org.klobt.Context;
import org.klobt.Error;
import org.klobt.value.BooleanValue;
import org.klobt.value.NumberValue;
import org.klobt.value.Value;

import java.util.Objects;

public class ProgramNode extends Node {
    private final Node node;

    public ProgramNode(int start, int end, Node node) {
        super(start, end);
        this.node = node;
    }

    @Override
    public String toString() {
        return "Program(" + node + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProgramNode that = (ProgramNode) o;

        return Objects.equals(node, that.node);
    }

    @Override
    public Value evaluate(Context context) {
        node.evaluate(context);
        
        return new NumberValue(0);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), node);
    }
}
