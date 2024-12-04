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

public class WhileNode extends Node {
    private final PureNode condition;
    private final Node body;

    public WhileNode(int start, int end, PureNode condition, Node body) {
        super(start, end);
        this.condition = condition;
        this.body = body;
    }

    @Override
    public String toString() {
        return "While(" + condition + ", " + body + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        WhileNode that = (WhileNode) o;

        return Objects.equals(condition, that.condition) && Objects.equals(body, that.body);
    }

    @Override
    public Value evaluate(Context context) throws BreakException, ContinueException, ReturnException {
        context.startLoop();

        if (condition.evaluate(context) instanceof BooleanValue conditionValue) {
            if (conditionValue.getValue()) {
                while (true) {
                    try {
                        body.evaluate(context);
                    } catch (BreakException e) {
                        break;
                    } catch (ContinueException e) {
                        //
                    }

                    if (condition.evaluate(context) instanceof BooleanValue value) {
                        if (!value.getValue()) {
                            break;
                        }
                    } else {
                        throw new Error(context.getInput(), getStart(), getEnd(), "Expected a boolean value inside the condition");
                    }
                }
            }
        } else {
            throw new Error(context.getInput(), getStart(), getEnd(), "Expected a boolean value inside the condition");
        }

        context.endLoop();

        return new NullValue();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), condition, body);
    }
}
