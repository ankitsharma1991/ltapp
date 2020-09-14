package com.accusterltapp.database;

public interface IDatabase {
    IKeyValueTable getSqlKeyValueTable();
    SharePreferenceKeyValueTable getSharedPreferenceKeyValueTable();
}
