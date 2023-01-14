package org.stellardev.wavecore.sql.builder;


import org.stellardev.wavecore.sql.SQLRequirement;

public class DeleteBuilder {

    private final String table;
    private final SQLRequirement[] sqlRequirements;

    public DeleteBuilder(String table, SQLRequirement... sqlRequirements) {
        this.table = table;
        this.sqlRequirements = sqlRequirements;
    }

    public String build() {
        if (sqlRequirements == null) {
            return "DELETE FROM " + table;
        }

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("DELETE FROM ").append(table).append(" WHERE ");
        int i = 1;
        for (SQLRequirement sqlRequirement : sqlRequirements) {
            queryBuilder.append(sqlRequirement.toWhere());
            if (i != sqlRequirements.length) {
                queryBuilder.append(" AND ");
            }
            i++;
        }
        return queryBuilder.toString();
    }

}
