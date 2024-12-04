package org.klobt.ast;

import org.klobt.Context;
import org.klobt.Error;
import org.klobt.control.BreakException;
import org.klobt.control.ContinueException;
import org.klobt.control.ReturnException;
import org.klobt.value.*;

import java.util.Objects;
import java.util.stream.Stream;

public class ForNode extends Node {
    private final String elementName;
    private final PureNode collection;
    private final Node body;

    public ForNode(int start, int end, String elementName, PureNode collection, Node body) {
        super(start, end);
        this.elementName = elementName;
        this.collection = collection;
        this.body = body;
    }

    @Override
    public String toString() {
        return "For(" + elementName + ", " + collection + ", " + body + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ForNode forNode = (ForNode) o;
        return Objects.equals(elementName, forNode.elementName) && Objects.equals(collection, forNode.collection) && Objects.equals(body, forNode.body);
    }

    @Override
    public Value evaluate(Context context) throws BreakException, ContinueException, ReturnException {
        context.startLoop();

        Stream<Value> valueStream;
        Value collectionValue = collection.evaluate(context);

        if (collectionValue instanceof StringValue collectionStringValue) {
            valueStream = collectionStringValue
                    .getValue()
                    .chars()
                    .mapToObj((int ch) -> new StringValue(String.valueOf((char) ch)));
        } else if (collectionValue instanceof ArrayValue collectionArrayValue) {
            valueStream = collectionArrayValue.getValues().stream();
        } else {
            throw new Error(context.getInput(), getStart(), getEnd(), "The collection should be a string or an array");
        }

        for (Value value : valueStream.toList()) {
            context.setVariable(elementName, value);
            try {
                body.evaluate(context);
            } catch (BreakException e) {
                break;
            } catch (ContinueException e) {
                //
            }
        }

        context.endLoop();

        return new NullValue();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), elementName, collection, body);
    }
}
