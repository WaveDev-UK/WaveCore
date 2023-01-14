package org.stellardev.wavecore.sql;

import lombok.Getter;

@Getter
public enum SQLType {

    SMALL_INTEGER("smallint"),
    INTEGER("integer"),
    BIG_INTEGER("bigint"),
    DECIMAL("decimal"),
    NUMERIC("numeric"),
    REAL("real"),
    DOUBLE_PRECISION("doubleprecision"),
    SMALL_SERIAL("smallserial"),
    SERIAL("serial"),
    BIG_SERIAL("bigserial"),
    MONEY("money"),
    CHARACTER("varchar(255)"),
    TEXT("text"),
    BYTEA("bytea"),
    TIMESTAMP("timestamp"),
    DATE("date"),
    TIME("time"),
    INTERVAL("interval"),
    BOOLEAN("boolean"),
    POINT("point"),
    LINE("line"),
    LSEG("lseg"),
    BOX("box"),
    PATH("path"),
    POLYGON("polygon"),
    CIRCLE("circle"),
    CIDR("cidr"),
    INET("inet"),
    MAC_ADDRESS("macaddr"),
    BIT("bit(8)"),
    UUID("uuid"),
    JSON("json"),
    INTEGER_ARRAY_1D("integer[]"),
    INTEGER_ARRAY_2D("integer[][]"),
    INTEGER_ARRAY_3D("integer[][][]"),
    XML("xml"),
    TS_VECTOR("tsvector");

    private final String type;

    SQLType(String type) {
        this.type = type;
    }


}
