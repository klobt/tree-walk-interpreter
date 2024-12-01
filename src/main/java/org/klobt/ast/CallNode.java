package org.klobt.ast;

import org.klobt.ArgumentList;
import org.klobt.Context;
import org.klobt.Error;
import org.klobt.value.Callable;
import org.klobt.value.Value;

import java.util.Objects;

public class CallNode extends PureNode {
    private final PureNode node;
    private final ArgumentList<PureNode> arguments;

    public CallNode(int start, int end, PureNode node, ArgumentList<PureNode> arguments) {
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

    @Override
    public Value evaluate(Context context) {
        if (node.evaluate(context) instanceof Callable callable) {
            return callable.call(context, this, arguments.map((PureNode node) -> node.evaluate(context)));
        } else {
            throw new Error(context.getInput(), getStart(), getEnd(), "Expected a callable");
        }
    }
}
