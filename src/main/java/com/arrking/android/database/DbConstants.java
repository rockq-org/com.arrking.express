package com.arrking.android.database;

/**
 * Created by Jim on 2015/1/19.
 */

public class DbConstants {

    public static final String DB_NAME = "express.db";
    public static final int DB_VERSION = 1;

    public static final String CREATE_ORDER_TABLE_SQL="CREATE TABLE IF NOT EXISTS OVERORDER(taskId INTEGER PRIMARY KEY" +
            " ,_id TEXT,_rev TEXT,billing INTEGER,status INTEGER,discount INTEGER,guestId TEXT," +
            "guestPassport TEXT,seatLng REAL,seatLat REAL,processDefId TEXT,rejected INTEGER, time DATETIME DEFAULT CURRENT_TIMESTAMP,location TEXT,alipayOrderId TEXT);";
    public static final String CREATE_FOOD_TABLE_SQL="CREATE TABLE IF NOT EXISTS FOOD(taskId INTEGER PRIMARY KEY" +
            " ,name TEXT,price REAL,count INTEGER);";

}

