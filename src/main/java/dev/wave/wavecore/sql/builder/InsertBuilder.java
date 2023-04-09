package dev.wave.wavecore.sql.builder;

import dev.wave.wavecore.sql.SQLData;

import java.util.ArrayList;
import java.util.List;

public class InsertBuilder {

    private final String table;
    private List<SQLData> dataList;

    public InsertBuilder(String table) {
        this.table = table;
        this.dataList = new ArrayList<>();
    }

    public InsertBuilder addData(SQLData sqlData) {
        this.dataList.add(sqlData);
        return this;
    }

    public String build() {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("INSERT INTO ").append(table).append(" (");
        int i = 1;
        for (SQLData sqlData : dataList) {
            queryBuilder.append(sqlData.getId());
            if (i < dataList.size()) {
                queryBuilder.append(", ");
            }
            i++;
        }
        queryBuilder.append(") VALUES (");
        i = 1;
        for (SQLData sqlData : dataList) {
            queryBuilder.append("'").append(sqlData.getData()).append("'");
            if (i < dataList.size()) {
                queryBuilder.append(", ");
            }
            i++;
        }
        queryBuilder.append(");");
        return queryBuilder.toString();
    }

}
