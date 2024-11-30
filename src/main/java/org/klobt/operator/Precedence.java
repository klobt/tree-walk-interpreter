package org.klobt.operator;

public enum Precedence {
    START(true),
    CONCATENATE(true),
    OR(true),
    AND(true),
    COMPARISON(true),
    ADD_SUBTRACT(true),
    MULTIPLY_DIVIDE(true),
    MODULO(true),
    POWER(false);

    private final boolean isLeftAssociative;

    Precedence(boolean isLeftAssociative) {
        this.isLeftAssociative = isLeftAssociative;
    }

    public boolean isLeftAssociative() {
        return isLeftAssociative;
    }

    public Precedence next() {
        Precedence[] days = Precedence.values();

        return days[ordinal() + 1];
    }

    public boolean isLast() {
        Precedence[] days = Precedence.values();

        return ordinal() == days.length - 1;
    }
}
