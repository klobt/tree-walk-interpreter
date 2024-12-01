package org.klobt.ast;

import java.util.Objects;

public class BranchNode extends Node {
    private final Node condition, ifStatement, elseStatement;

    public BranchNode(int start, int end, Node condition, Node ifStatement, Node elseStatement) {
        super(start, end);
        this.condition = condition;
        this.ifStatement = ifStatement;
        this.elseStatement = elseStatement;
    }

    @Override
    public String toString() {
        return "Branch(" + condition + ", " + ifStatement + ", " + elseStatement + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BranchNode that = (BranchNode) o;

        return Objects.equals(condition, that.condition) && Objects.equals(ifStatement, that.ifStatement) && Objects.equals(elseStatement, that.elseStatement);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), condition, ifStatement, elseStatement);
    }
}
