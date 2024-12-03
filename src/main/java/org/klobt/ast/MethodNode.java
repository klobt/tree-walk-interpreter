package org.klobt.ast;

import org.klobt.Context;
import org.klobt.Error;
import org.klobt.value.FunctionValue;
import org.klobt.value.MethodValue;
import org.klobt.value.ObjectValue;
import org.klobt.value.Value;

import java.util.Objects;

public class MethodNode extends PureNode {
    private final PureNode node;
    private final String methodName;

    public MethodNode(int start, int end, PureNode node, String methodName) {
        super(start, end);
        this.node = node;
        this.methodName = methodName;
    }

    @Override
    public String toString() {
        return "Method(" + node + ", " + methodName + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MethodNode that = (MethodNode) o;

        return Objects.equals(node, that.node) && Objects.equals(methodName, that.methodName);
    }

    @Override
    public Value evaluate(Context context) {
        Value value = node.evaluate(context);

        if (value instanceof ObjectValue objectValue) {
            if (objectValue.getValues().containsKey(methodName)) {
                if (objectValue.getValues().get(methodName) instanceof FunctionValue functionValue) {
                    return new MethodValue(objectValue, functionValue);
                } else {
                    throw new Error(context.getInput(), getStart(), getEnd(), "Method must be a function");
                }
            } else {
                throw new Error(context.getInput(), getStart(), getEnd(), "Method not found");
            }
        } else {
            throw new Error(context.getInput(), getStart(), getEnd(), "Value must be an object");
        }
    }
}
