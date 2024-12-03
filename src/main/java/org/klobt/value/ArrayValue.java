package org.klobt.value;

import org.klobt.io.Writer;

import java.util.List;
import java.util.Objects;

public class ArrayValue extends Value {
    private final List<Value> values;

    public ArrayValue(List<Value> values) {
        this.values = values;
    }


    public List<Value> getValues() {
        return values;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ArrayValue that = (ArrayValue) o;

        return Objects.equals(values, that.values);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Array(");

        boolean isFirst = true;
        for (Value value : values) {
            if (!isFirst) {
                stringBuilder.append(", ");
            }
            stringBuilder.append(value);
            isFirst = false;
        }

        stringBuilder.append(")");

        return stringBuilder.toString();
    }

    @Override
    public void print(Writer writer) {
        writer.write("array(");

        boolean isFirst = true;
        for (Value value : values) {
            if (!isFirst) {
                writer.write(", ");
            }
            value.print(writer);
            isFirst = false;
        }

        writer.write(")");
    }
}
