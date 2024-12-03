package org.klobt.value;

import org.klobt.io.Writer;

import java.util.HashMap;
import java.util.Objects;

public class ObjectValue extends Value {
    private final HashMap<String, Value> values;

    public ObjectValue(HashMap<String, Value> values) {
        this.values = values;
    }


    public HashMap<String, Value> getValues() {
        return values;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ObjectValue that = (ObjectValue) o;

        return Objects.equals(values, that.values);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Object(");

        boolean isFirst = true;
        for (String name : values.keySet()) {
            if (!isFirst) {
                stringBuilder.append(", ");
            }
            stringBuilder.append(name).append("=").append(values.get(name));
            isFirst = false;
        }

        stringBuilder.append(")");

        return stringBuilder.toString();
    }

    @Override
    public void print(Writer writer) {
        writer.write("object(");

        boolean isFirst = true;
        for (String name : values.keySet()) {
            if (!isFirst) {
                writer.write(", ");
            }
            writer.write(name + "=" + values.get(name));
            isFirst = false;
        }


        writer.write(")");
    }
}
