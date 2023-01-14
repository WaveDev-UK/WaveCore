package org.stellardev.wavecore.sql;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SQLRequirement {

    private final String id;
    private final Object data;

    public String toWhere() {
        return id + "=" + "'" + data + "'";
    }

}
