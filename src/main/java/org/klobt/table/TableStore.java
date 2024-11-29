package org.klobt.table;

import java.util.HashMap;

public class TableStore {
    private final HashMap<TableId, Table> tables;
    private int nextId;

    public TableStore() {
        tables = new HashMap<>();
        nextId = 0;
    }

    public TableId createTable() {
        int id = nextId++;

        TableId tableId = new TableId(id);
        tables.put(tableId, new Table());

        return tableId;
    }

    public Table getTable(TableId id) {
        return tables.get(id);
    }

    public void removeTable(TableId id) {
        tables.remove(id);
    }
}
