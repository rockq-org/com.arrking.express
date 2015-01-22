package com.arrking.android.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.arrking.express.model.DBOrder;
import com.arrking.express.model.Food;
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

    public void insert(String taskId,Order order){
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put("taskId", taskId);
            values.put("_id", order.get_id());
            values.put("_rev",order.get_rev());
            values.put("billing", order.getBilling());
            values.put("status",order.getStatus());
            values.put("discount", order.getDiscount());
            values.put("guestId", order.getGuestId());
            values.put("guestPassport",order.getGuestPassport());
            values.put("seatLng", order.getSeatLng());
            values.put("seatLat",order.getSeatLat());
            values.put("processDefId",order.getProcessDefId());
            values.put("rejected", order.isRejected());
            values.put("alipayOrderId",order.getAlipayOrderId());
            db.insertOrThrow("OVERORDER", null, values);
            db.setTransactionSuccessful();
            for(int i=0;i<order.getFoods().size();i++){
                Food f=order.getFoods().get(i);
                ContentValues value = new ContentValues();
                value.put("taskId", taskId);
                value.put("name", f.getName());
                value.put("price",f.getPrice());
                value.put("count",f.getCount());
                db.insertOrThrow("FOOD", null, value);
            }
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
