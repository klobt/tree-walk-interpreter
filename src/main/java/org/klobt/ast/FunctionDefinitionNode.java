package org.klobt.ast;

import org.klobt.Context;
import org.klobt.operator.UnaryOperator;
import org.klobt.value.FunctionValue;
import org.klobt.value.Value;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class FunctionDefinitionNode extends PureNode {
    private final List<String> positionalArguments;
    private final HashMap<String, PureNode> keywordArguments;
    private final Node body;

    public FunctionDefinitionNode(int start, int end, List<String> positionalArguments, HashMap<String, PureNode> keywordArguments, Node body) {
        super(start, end);
        this.positionalArguments = positionalArguments;
        this.keywordArguments = keywordArguments;
        this.body = body;
    }

    @Override
    public String toString() {
        return "FunctionDefinition(" + positionalArguments + ", " + keywordArguments + ", " + body + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FunctionDefinitionNode that = (FunctionDefinitionNode) o;

        return Objects.equals(positionalArguments, that.positionalArguments) && Objects.equals(keywordArguments, that.keywordArguments) && Objects.equals(body, that.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), positionalArguments, keywordArguments, body);
    }

    @Override
    public Value evaluate(Context context) {
        HashMap<String, Value> keywordArgumentsWithValues = new HashMap<>();
        for (String argument : keywordArguments.keySet()) {
            keywordArgumentsWithValues.put(argument, keywordArguments.get(argument).evaluate(context));
        }

        return new FunctionValue(positionalArguments, keywordArgumentsWithValues, body);
    }
}
