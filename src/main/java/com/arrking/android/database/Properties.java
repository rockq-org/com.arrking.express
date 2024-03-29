package com.arrking.android.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.arrking.android.exception.DBException;

import java.util.HashMap;

import cn.trinea.android.common.util.StringUtils;

/**
 * Store Key Value Pair in database
 * a singleton class
 * Created by hain on 05/01/2015.
 */
public class Properties implements SelectionQueryBuilder.Op {

    private static final String CLASSNAME = Properties.class.getSimpleName();
    public static final String DB_NAME = "com.arrking.express";
    public static final String DB_TABLE = "properties";
    public static final int DB_VERSION = 1;
    private static Properties instance = null;
    private static DBOpenHelper dbHelper = null;

    private SQLiteDatabase db;

    private Properties() {
    }

    public static Properties getInstance(Context ctx) {
        if (instance == null) {
            instance = new Properties();
            instance.init(ctx);
        }
        return instance;
    }

    public Long set(String key, String value) {
        ContentValues cv = new ContentValues();
        cv.put("name", key);
        cv.put("value", value);
        return db.insert(DB_TABLE, null, cv);
    }

    public void update(String key, String value) throws DBException {
        Long id = getId(key);
        if (id > -1L) {
            ContentValues c = new ContentValues();
            c.put("name", key);
            c.put("value", value);
            db.update(DB_TABLE, c, "_id=?", new String[]{Long.toString(id)});
        } else {
            throw new DBException(String.format("name [ %s ] does not exist in properties db.", key));
        }
    }

    // save the key with value, update the key, if the key exist already.
    public void save(String key, String value) {
        if (hasKey(key)) {
            try {
                update(key, value);
            } catch (DBException e) {
                Log.w(CLASSNAME, "save", e);
            }
        } else {
            set(key, value);
        }
    }

    // remove key-value pair
    public void rm(String key) {
        if (hasKey(key)) {
            db.delete(DB_TABLE, String.format("_id=%d", getId(key)), null);
        }
    }

    // check if the key exist
    public boolean hasKey(String key) {
        return getId(key) == -1L ? false : true;
    }

    // if the key does not exist, return -1L
    private Long getId(String key) {
        Long id = -1L;
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(DB_TABLE);
        SelectionQueryBuilder sqb = new SelectionQueryBuilder();
        sqb.expr("name", EQ, key);
        //  query (SQLiteDatabase db, String[] projectionIn, String selection, String[] selectionArgs, String groupBy, String having, String sortOrder)
        Cursor c = queryBuilder.query(db, null, sqb.toString(), sqb.getArgsArray(), null, null, null);
        if (c.moveToFirst()) {
            id = c.getLong(0);
            Log.d(CLASSNAME, String.format("get key %s id %s", key, id));
        } else {
            Log.d(CLASSNAME, String.format("can not find value with key %s", key));
        }
        return id;
    }

    // if the key does not exist, return null.
    public String get(String key) {
        String id = null;
        String value = null;
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(DB_TABLE);
        SelectionQueryBuilder sqb = new SelectionQueryBuilder();
        sqb.expr("name", EQ, key);
        //  query (SQLiteDatabase db, String[] projectionIn, String selection, String[] selectionArgs, String groupBy, String having, String sortOrder)
        Cursor c = queryBuilder.query(db, null, sqb.toString(), sqb.getArgsArray(), null, null, null);
        if (c.moveToFirst()) {
            id = c.getString(0);
            value = c.getString(2);
            Log.d(CLASSNAME, String.format("get key %s value %s", key, value));
        } else {
            Log.d(CLASSNAME, String.format("can not find value with key %s", key));
        }
        return value;
    }

    /*
     * Init DBHelper
     */
    private void init(Context ctx) {
        dbHelper = new DBOpenHelper(ctx);
        db = dbHelper.getWritableDatabase();
    }

    private static class DBOpenHelper extends SQLiteOpenHelper {

        private static final String CLASSNAME = DBOpenHelper.class.getSimpleName();
        private static final String DB_CREATE = "CREATE TABLE "
                + Properties.DB_TABLE
                + " (_id INTEGER PRIMARY KEY, name TEXT UNIQUE NOT NULL, value TEXT);";

        // private static final String DB_UPDATE = "";

        public DBOpenHelper(final Context context) {
            super(context, Properties.DB_NAME, null, Properties.DB_VERSION);
        }

        @Override
        public void onCreate(final SQLiteDatabase db) {
            Log.v(CLASSNAME, Properties.CLASSNAME + " OpenHelper Creating database");
            try {
                db.execSQL(DBOpenHelper.DB_CREATE);
            } catch (SQLException e) {
                Log.e(CLASSNAME, Properties.CLASSNAME, e);
            }
        }

        @Override
        public void onOpen(final SQLiteDatabase db) {
            super.onOpen(db);
            Log.v(CLASSNAME, Properties.CLASSNAME + " OpenHelper Opening database");
        }

        @Override
        public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {
            Log.w(CLASSNAME, Properties.CLASSNAME + " OpenHelper Upgrading database from version "
                    + oldVersion + " to " + newVersion + " all data will be clobbered");
            db.execSQL("DROP TABLE IF EXISTS " + Properties.DB_TABLE);
            this.onCreate(db);
        }
    }


}
