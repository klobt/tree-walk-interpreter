package org.klobt;

import org.klobt.io.Writer;
import org.klobt.value.NullValue;
import org.klobt.value.Value;

import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class Context {
    private final String input;
    private final Writer writer;
    private final Stack<HashMap<String, Value>> scopes;
    private int functionDepth = 0;
    private int loopDepth = 0;

    public Context(String input, Writer writer) {
        this.input = input;
        this.writer = writer;
        this.scopes = new Stack<>();
        this.scopes.add(new HashMap<>());
    }

    public String getInput() {
        return input;
    }

    public Writer getWriter() {
        return writer;
    }

    public Value getVariable(String name) {
        Value value;

        for (HashMap<String, Value> namespace : scopes.reversed()) {
            value = namespace.get(name);

            if (value != null) {
                return value;
            }
        }

        return new NullValue();
    }

    public void setVariable(String name, Value value) {
        scopes.peek().put(name, value);
    }

    public void pushScope() {
        this.scopes.push(new HashMap<>());
    }

    public void popScope() {
        this.scopes.pop();
    }

    public void startFunction() {
        functionDepth++;
    }

    public void endFunction() {
        functionDepth--;
    }

    public void startLoop() {
        loopDepth++;
    }

    public void endLoop() {
        loopDepth--;
    }

    public int getFunctionDepth() {
        return functionDepth;
    }

    public int getLoopDepth() {
        return loopDepth;
    }
}
