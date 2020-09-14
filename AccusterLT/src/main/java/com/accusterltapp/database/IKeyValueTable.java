package com.accusterltapp.database;

public interface IKeyValueTable {
    void set(String key, String value);

    String get(String key);

    String get(String key, String defaultValue);

    void delete(String key);

}
