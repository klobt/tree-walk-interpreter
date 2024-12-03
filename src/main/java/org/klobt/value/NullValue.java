package org.klobt.value;

import org.klobt.io.Writer;

public class NullValue extends Value {
    @Override
    public boolean equals(Object o) {
        return o != null && getClass() == o.getClass();
    }

    @Override
    public String toString() {
        return "Null";
    }

    @Override
    public void print(Writer writer) {
        writer.write("null");
    }
}
