package org.klobt.value;

import org.klobt.ArgumentList;
import org.klobt.Context;
import org.klobt.ast.Node;
import org.klobt.control.BreakException;
import org.klobt.control.ContinueException;
import org.klobt.control.ReturnException;
import org.klobt.io.Writer;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class FunctionValue extends Value implements Callable {
    private final List<String> positionalArguments;
    private final HashMap<String, Value> keywordArguments;
    private final Node body;

    public FunctionValue(List<String> positionalArguments, HashMap<String, Value> keywordArguments, Node body) {
        this.positionalArguments = positionalArguments;
        this.keywordArguments = keywordArguments;
        this.body = body;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FunctionValue that = (FunctionValue) o;

        return Objects.equals(positionalArguments, that.positionalArguments) && Objects.equals(keywordArguments, that.keywordArguments) && Objects.equals(body, that.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), positionalArguments, keywordArguments, body);
    }

    @Override
    public void print(Writer writer) {
        writer.write("<function>");
    }

    @Override
    public String toString() {
        // TODO prettify it
        return "Function(" + positionalArguments + ", " + keywordArguments + ", " + body + ")";
    }

    @Override
    public Value call(Context context, Node node, ArgumentList<Value> arguments) {
        context.startFunction();
        context.pushScope();

        for (int i = 0; i < positionalArguments.size(); i++) {
            context.setVariable(
                    positionalArguments.get(i),
                    i < arguments.getPositionalArguments().size()
                            ? arguments.getPositionalArguments().get(i)
                            : new NullValue()
            );
        }

        for (String argumentName : keywordArguments.keySet()) {
            context.setVariable(
                    argumentName,
                    arguments.getKeywordArguments().getOrDefault(
                            argumentName,
                            keywordArguments.get(argumentName)
                    )
            );
        }

        Value returnValue = new NullValue();

        try {
            returnValue = body.evaluate(context);
        } catch (BreakException | ContinueException e) {
            //
        } catch (ReturnException e) {
            returnValue = e.getValue();
        }

        context.popScope();
        context.endFunction();

        return returnValue;
    }
}
