package org.klobt.table;

public class TableId {
    private final int id;

    public TableId(int id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TableId tableId = (TableId) o;

        return id == tableId.id;
    }
}
