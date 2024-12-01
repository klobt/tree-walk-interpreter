package org.klobt.ast;

import org.klobt.ArgumentList;
import org.klobt.Context;
import org.klobt.value.NullValue;
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
        // TODO implement array index
        return new NullValue();
    }
}
