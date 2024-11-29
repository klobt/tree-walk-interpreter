package org.klobt.table;

import org.klobt.value.NumberValue;
import org.klobt.value.Value;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Table {
    private final HashMap<Value, Value> hashMap;
    private final List<Value> array;

    public Table() {
        this.hashMap = new HashMap<>();
        this.array = new ArrayList<>();
    }

    public void add(Value key, Value value) {
        if (key instanceof NumberValue) {
            double keyValue = ((NumberValue) key).getValue();
            int index = (int) keyValue;

            if (keyValue == index) {
                if (index == array.size()) {
                    array.add(value);
                    for (int i = index; hashMap.containsKey(new NumberValue(i)); i++) {
                        array.add(hashMap.get(new NumberValue(i)));
                        hashMap.remove(new NumberValue(i));
                    }

                    return;
                } else if (index < array.size()) {
                    array.set(index, value);

                    return;
                }
            }
        }

        hashMap.put(key, value);
    }

    public int getArraySize() {
        return array.size();
    }
}
