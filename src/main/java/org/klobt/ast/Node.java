package org.klobt.ast;

public abstract class Node {
    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
