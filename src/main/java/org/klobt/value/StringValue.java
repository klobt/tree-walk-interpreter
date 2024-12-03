package org.klobt.value;

import org.klobt.io.Writer;

import java.util.Objects;

public class StringValue extends Value {
    private String value;

    public StringValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StringValue that = (StringValue) o;

        return Objects.equals(value, that.value);
    }

    @Override
    public String toString() {
        return "String(" + value + ")";
    }

    @Override
    public void print(Writer writer) {
        writer.write(value);
    }
}
