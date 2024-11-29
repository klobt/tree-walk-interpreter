package org.klobt.value;

public abstract class Value {
    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
