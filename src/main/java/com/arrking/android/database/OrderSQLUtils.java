package com.arrking.android.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.arrking.express.model.DBOrder;
import com.arrking.express.model.Order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Jim on 2015/1/19.
 */
public class OrderSQLUtils{

    private DBHelper dbHelper;
    protected SQLiteDatabase db;
    public OrderSQLUtils(Context context){
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public void insert(DBOrder dborder){
        db.beginTransaction();
        try {
            db.execSQL("INSERT INTO order VALUES(?, ?)", new Object[]{dborder.getId(),dborder.getDate()});
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    public List<DBOrder> query(){
        ArrayList<DBOrder> orders = new ArrayList<DBOrder>();
        Cursor c = db.rawQuery("select * from table order",null);
        while (c.moveToNext()) {
            DBOrder order = new DBOrder();
            order.setId(c.getInt(c.getColumnIndex("id"))) ;
            order.setDate(new Date(c.getString(c.getColumnIndex("date"))));
            orders.add(order);
        }
        c.close();
        return orders;
    }
}
