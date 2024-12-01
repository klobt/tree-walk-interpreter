package org.klobt.ast;

import org.klobt.Context;
import org.klobt.control.BreakException;
import org.klobt.control.ContinueException;
import org.klobt.value.NullValue;
import org.klobt.value.Value;

import java.util.List;
import java.util.Objects;

public class BlockNode extends Node {
    private final List<Node> nodes;

    public BlockNode(int start, int end, List<Node> nodes) {
        super(start, end);
        this.nodes = nodes;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Block(");

        boolean isFirst = true;

        for (Node node : nodes) {
            if (!isFirst) {
                stringBuilder.append(", ");
            }
            stringBuilder.append(node);
            isFirst = false;
        }

        stringBuilder.append(")");

        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BlockNode that = (BlockNode) o;

        if (nodes.size() != that.nodes.size()) {
            return false;
        }

        for (int i = 0; i < nodes.size(); i++) {
            if (!nodes.get(i).equals(that.nodes.get(i))) {
                return false;
            }
        }

        return true;
    }

    @Override
    public Value evaluate(Context context) throws BreakException, ContinueException {
        for (Node node : nodes) {
            node.evaluate(context);
        }

        return new NullValue();
    }
}
