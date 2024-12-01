package org.klobt;

import org.klobt.io.Writer;
import org.klobt.value.NullValue;
import org.klobt.value.Value;

import java.util.HashMap;

public class Context {
    private final String input;
    private final Writer writer;
    private final HashMap<String, Value> namespace;

    public Context(String input, Writer writer) {
        this.input = input;
        this.writer = writer;
        this.namespace = new HashMap<>();
    }

    public String getInput() {
        return input;
    }

    public Writer getWriter() {
        return writer;
    }

    public Value getVariable(String name) {
        return namespace.getOrDefault(name, new NullValue());
    }

    public void setVariable(String name, Value value) {
        namespace.put(name, value);
    }
}
