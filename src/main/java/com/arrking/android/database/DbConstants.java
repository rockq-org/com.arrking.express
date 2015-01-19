package com.arrking.android.database;

/**
 * Created by Jim on 2015/1/19.
 */

public class DbConstants {

    public static final String DB_NAME = "express.db";
    public static final int DB_VERSION = 1;

    public static final String CREATE_TABLE_SQL="CREATE TABLE IF NOT EXISTS order(id INTEGER PRIMARY KEY ,operatedate date)";
}

