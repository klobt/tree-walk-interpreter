package org.klobt.ast;

import org.klobt.Context;
import org.klobt.Error;
import org.klobt.value.*;

import java.util.Objects;

public class MemberNode extends PureNode {
    private final PureNode node;
    private final String memberName;

    public MemberNode(int start, int end, PureNode node, String memberName) {
        super(start, end);
        this.node = node;
        this.memberName = memberName;
    }

    @Override
    public String toString() {
        return "Member(" + node + ", " + memberName + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MemberNode that = (MemberNode) o;

        return Objects.equals(node, that.node) && Objects.equals(memberName, that.memberName);
    }

    @Override
    public Value evaluate(Context context) {
        Value value = node.evaluate(context);

        if (value instanceof ObjectValue objectValue) {
            if (objectValue.getValues().containsKey(memberName)) {
                return objectValue.getValues().get(memberName);
            } else {
                throw new Error(context.getInput(), getStart(), getEnd(), "Member variable not found");
            }
        } else {
            throw new Error(context.getInput(), getStart(), getEnd(), "Value must be an object");
        }
    }
}
