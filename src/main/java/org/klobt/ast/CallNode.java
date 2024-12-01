package org.klobt.ast;

import org.klobt.ArgumentList;

import java.util.Objects;

public class CallNode extends Node {
    private final Node node;
    private final ArgumentList<Node> arguments;

    public CallNode(int start, int end, Node node, ArgumentList<Node> arguments) {
        super(start, end);
        this.node = node;
        this.arguments = arguments;
    }

    @Override
    public String toString() {
        return "Call(" + node + ", " + arguments + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CallNode that = (CallNode) o;

        return Objects.equals(node, that.node) && Objects.equals(arguments, that.arguments);
    }
}
