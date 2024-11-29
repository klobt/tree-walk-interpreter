package org.klobt.value;


import org.klobt.table.TableId;

import java.util.Objects;

public class TableValue extends Value {
    private final TableId id;

    public TableValue(TableId id) {
        this.id = id;
    }

    public TableId getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TableValue that = (TableValue) o;

        return Objects.equals(id, that.id);
    }

    @Override
    public String toString() {
        return "Table(" + id + ")";
    }
}
