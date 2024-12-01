package org.klobt;

import org.klobt.io.Writer;

public class Context {
    private final String input;
    private final Writer writer;

    public Context(String input, Writer writer) {
        this.input = input;
        this.writer = writer;
    }

    public String getInput() {
        return input;
    }

    public Writer getWriter() {
        return writer;
    }
}
