package org.klobt.ast;

import org.klobt.Context;
import org.klobt.value.Value;

public abstract class LHSNode extends PureNode {
    public LHSNode(int start, int end) {
        super(start, end);
    }

    public abstract void assign(Context context, Value rhs);
}
