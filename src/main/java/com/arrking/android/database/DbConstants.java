package com.arrking.android.database;

/**
 * Created by Jim on 2015/1/19.
 */

public class DbConstants {

    public static final String DB_NAME = "express.db";
    public static final int DB_VERSION = 1;

    public static final String CREATE_ORDER_TABLE_SQL="CREATE TABLE IF NOT EXISTS OVERORDER(taskId INTEGER PRIMARY KEY" +
            " ,_id TEXT,_rev TEXT,billing INTEGER,status INTEGER,discount INTEGER,guestId TEXT," +
            "guestPassport TEXT,seatLng REAL,seatLat REAL,processDefId TEXT,rejected INTEGER, alipayOrderId TEXT);";
    public static final String CREATE_FOOD_TABLE_SQL="CREATE TABLE IF NOT EXISTS FOOD(taskId INTEGER PRIMARY KEY" +
            " ,name TEXT,price REAL,count INTEGER);";
//
//    "_id": "25376196dc67938c4f301fa664340d80",
//            "_rev": "2-c6c665e741ff38bde323230b8f9932da",
//            "status": 2,
//            "billing": 28,
//            "discount": 1,
//            "foods": [
//    {
//        "name": "黑椒牛肉套餐",
//            "price": 28,
//            "count": 1
//    }
//    ],
//            "guestId": "hain_wang@foxmail.com",
//            "guestPassport": "local",
//            "seatLng": "25.0",
//            "seatLat": "75.0",
//            "processDefId": "ofo:43:1",
//            "rejected": false,
//            "alipayOrderId": "2015010446753962"
}

