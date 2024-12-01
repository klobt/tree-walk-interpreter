package org.klobt.io;

public class StringWriter implements Writer {
    private final StringBuilder stringBuilder = new StringBuilder();

    public String getString() {
        return stringBuilder.toString();
    }

    @Override
    public void write(String contents) {
        stringBuilder.append(contents);
    }
}
