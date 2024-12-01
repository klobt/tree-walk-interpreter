package org.klobt.value;

import java.util.Objects;

public class NullValue extends Value {
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "Null";
    }
}
