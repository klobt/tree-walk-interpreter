package org.klobt.value;

import org.klobt.io.Writer;

public class NumberValue extends Value {
    private final double value;

    public NumberValue(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        NumberValue that = (NumberValue) o;

        return Double.compare(value, that.value) == 0;
    }

    @Override
    public String toString() {
        return "Number(" + value + ")";
    }

    @Override
    public void print(Writer writer) {
        writer.write(String.valueOf(value));
    }
}
