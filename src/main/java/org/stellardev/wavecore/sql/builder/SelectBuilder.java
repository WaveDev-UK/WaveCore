package org.stellardev.wavecore.sql.builder;

import org.stellardev.wavecore.sql.SQLRequirement;

public class SelectBuilder {

    private final String table;
    private SQLRequirement[] sqlRequirements;
    private final String[] ids;

    public SelectBuilder(String table, String... ids) {
        this.table = table;
        this.ids = ids;
    }

    public SelectBuilder insertRequirements(SQLRequirement... sqlRequirements) {
        this.sqlRequirements = sqlRequirements;
        return this;
    }

    public String build() {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("SELECT ");
        if (ids == null) {
            queryBuilder.append("* FROM ").append(table);
        } else {
            int i = 1;
            for (String id : ids) {
                queryBuilder.append(id);
                if (i < ids.length) {
                    queryBuilder.append(", ");
                }
                i++;
            }
        }
        if (sqlRequirements != null) {
            queryBuilder.append(" WHERE ");
            int i = 1;
            for (SQLRequirement sqlRequirement : sqlRequirements) {
                queryBuilder.append(sqlRequirement.toWhere());
                if (i < sqlRequirements.length) {
                    queryBuilder.append(", ");
                }
                i++;
            }

        }
        return queryBuilder.toString();

    }


}
