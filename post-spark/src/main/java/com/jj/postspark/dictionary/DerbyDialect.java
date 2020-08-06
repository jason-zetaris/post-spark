package com.jj.postspark.dictionary;

import org.hibernate.dialect.DerbyTenSevenDialect;

import java.sql.Types;

public class DerbyDialect extends DerbyTenSevenDialect {
    public DerbyDialect() {
        super();
        registerColumnType(Types.CLOB, "clob");
    }
}
