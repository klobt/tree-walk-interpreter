package org.klobt;

import org.klobt.table.TableStore;

public class Context {
    private final String input;
    private final TableStore tableStore;

    public Context(String input) {
        this.input = input;
        tableStore = new TableStore();
    }

    public String getInput() {
        return input;
    }

    public TableStore getTableStore() {
        return tableStore;
    }
}
