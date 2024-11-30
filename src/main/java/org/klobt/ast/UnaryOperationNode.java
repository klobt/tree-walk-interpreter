package org.klobt.ast;

import org.klobt.operator.UnaryOperator;

import java.util.Objects;

public class UnaryOperationNode extends Node {
    private final Node node;
    private final UnaryOperator operator;

    public UnaryOperationNode(Node node, UnaryOperator operator) {
        this.node = node;
        this.operator = operator;
    }

    @Override
    public String toString() {
        return "UnaryOperation(" + operator + ", " + node + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UnaryOperationNode that = (UnaryOperationNode) o;

        return Objects.equals(node, that.node) && Objects.equals(operator, that.operator);
    }
}
