package org.klobt.value;

import org.klobt.io.Writer;

public abstract class Value {
    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    public abstract void print(Writer writer);
}
