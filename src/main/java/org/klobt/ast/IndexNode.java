package org.klobt.ast;

import org.klobt.Context;
import org.klobt.Error;
import org.klobt.value.ArrayValue;
import org.klobt.value.NumberValue;
import org.klobt.value.StringValue;
import org.klobt.value.Value;

import java.util.Objects;

public class IndexNode extends PureNode {
    private final PureNode node;
    private final PureNode index;

    public IndexNode(int start, int end, PureNode node, PureNode index) {
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

    @Override
    public Value evaluate(Context context) {
        int i;

        if (index.evaluate(context) instanceof NumberValue indexValue) {
            i = (int) indexValue.getValue();
        } else {
            throw new Error(context.getInput(), getStart(), getEnd(), "Index must be a number");
        }

        Value value = node.evaluate(context);

        if (value instanceof StringValue stringValue) {
            if (i < stringValue.getValue().length()) {
                return new StringValue(stringValue.getValue().substring(i, i + 1));
            } else {
                throw new Error(context.getInput(), getStart(), getEnd(), "Index beyond bounds");
            }
        } else if (value instanceof ArrayValue arrayValue) {
            if (i < arrayValue.getValues().size()) {
                return arrayValue.getValues().get(i);
            } else {
                throw new Error(context.getInput(), getStart(), getEnd(), "Index beyond bounds");
            }
        } else {
            throw new Error(context.getInput(), getStart(), getEnd(), "Value must be a string or an array");
        }
    }
}
