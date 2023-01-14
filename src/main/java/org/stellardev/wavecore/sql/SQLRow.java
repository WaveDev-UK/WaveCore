package org.stellardev.wavecore.sql;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter
public class SQLRow {

    private final int rowId;
    private final Map<String, Object> data;

    public SQLRow(int rowId) {
        this.rowId = rowId;
        this.data = new HashMap<>();
    }

    public void add(String colId, Object o) {
        this.data.put(colId, o);
    }

}
