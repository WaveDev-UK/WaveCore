package org.stellardev.wavecore.sql;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SQLColumn {

    private final String id;
    private final SQLType type;

}
