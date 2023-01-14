package org.stellardev.wavecore.sql;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SQLData {

    private final String id;
    private final Object data;

    public String toWhere() {
        return id + "='" + data + "'";
    }

}
