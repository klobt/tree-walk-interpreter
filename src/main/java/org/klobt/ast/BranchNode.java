package org.klobt.ast;

import org.klobt.Context;
import org.klobt.Error;
import org.klobt.control.BreakException;
import org.klobt.control.ContinueException;
import org.klobt.control.ReturnException;
import org.klobt.value.BooleanValue;
import org.klobt.value.NullValue;
import org.klobt.value.Value;

import java.util.Objects;

public class BranchNode extends Node {
    private final PureNode condition;
    private final Node ifStatement, elseStatement;

    public BranchNode(int start, int end, PureNode condition, Node ifStatement, Node elseStatement) {
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
    public Value evaluate(Context context) throws BreakException, ContinueException, ReturnException {
        if (condition.evaluate(context) instanceof BooleanValue conditionValue) {
            if (conditionValue.getValue()) {
                return ifStatement.evaluate(context);
            } else if (elseStatement != null) {
                return elseStatement.evaluate(context);
            }
        } else {
            throw new Error(context.getInput(), getStart(), getEnd(), "Expected a boolean value inside the condition");
        }

        return new NullValue();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), condition, ifStatement, elseStatement);
    }
}
