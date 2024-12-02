package org.klobt.control;

import org.klobt.value.Value;

public class ReturnException extends Throwable {
    private final Value value;

    public ReturnException(Value value) {
        super("ReturnException(" + value + ")");
        this.value = value;
    }

    public Value getValue() {
        return value;
    }
}
