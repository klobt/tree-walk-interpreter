package org.klobt.ast;

import org.klobt.Context;
import org.klobt.Error;
import org.klobt.control.ReturnException;
import org.klobt.value.Value;

import java.util.Objects;

public class ReturnNode extends Node {
    private final PureNode node;

    public ReturnNode(int start, int end, PureNode node) {
        super(start, end);
        this.node = node;
    }

    @Override
    public String toString() {
        return "Return";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ReturnNode that = (ReturnNode) o;

        return Objects.equals(node, that.node);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), node);
    }

    @Override
    public Value evaluate(Context context) throws ReturnException {
        if (context.getFunctionDepth() > 0) {
            throw new ReturnException(node.evaluate(context));
        } else {
            throw new Error(context.getInput(), getStart(), getEnd(), "Can't call return outside a function");
        }
    }
}
