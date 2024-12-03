package org.klobt.value;

import org.klobt.io.Writer;

public class BooleanValue extends Value {
    private final boolean value;

    public BooleanValue(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BooleanValue that = (BooleanValue) o;

        return value == that.value;
    }

    @Override
    public String toString() {
        return "Boolean(" + value + ")";
    }

    @Override
    public void print(Writer writer) {
        writer.write(value ? "true" : "false");
    }
}
