package org.klobt.ast;

import org.klobt.operator.BinaryOperator;

import java.util.Objects;

public class BinaryOperationNode extends Node {
    private final Node leftNode, rightNode;
    private final BinaryOperator operator;

    public BinaryOperationNode(Node leftNode, Node rightNode, BinaryOperator operator) {
        this.leftNode = leftNode;
        this.rightNode = rightNode;
        this.operator = operator;
    }

    @Override
    public String toString() {
        return "BinaryOperation(" + leftNode + ", " + operator + ", " + rightNode + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BinaryOperationNode that = (BinaryOperationNode) o;

        return Objects.equals(leftNode, that.leftNode) && Objects.equals(rightNode, that.rightNode) && Objects.equals(operator, that.operator);
    }
}
