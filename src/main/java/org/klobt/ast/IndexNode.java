package org.klobt.ast;

import org.klobt.ArgumentList;

import java.util.Objects;

public class IndexNode extends Node {
    private final Node node;
    private final Node index;

    public IndexNode(int start, int end, Node node, Node index) {
        super(start, end);
        this.node = node;
        this.index = index;
    }

    @Override
    public String toString() {
        return "Index(" + node + ", " + index + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        IndexNode that = (IndexNode) o;

        return Objects.equals(node, that.node) && Objects.equals(index, that.index);
    }
}
