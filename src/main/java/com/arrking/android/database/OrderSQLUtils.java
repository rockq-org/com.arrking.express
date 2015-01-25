package com.arrking.android.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.arrking.android.util.TimeUtils;
import com.arrking.express.model.Food;
import com.arrking.express.model.Order;

import java.text.SimpleDateFormat;
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

    public void insert(String taskId,String location,Order order){
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put("taskId", taskId);
            values.put("location", location);
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

    /**
     * 查询数据库中所有已处理的task
     * @return
     */
    public List<Order> queryOrders(){
        ArrayList<Order> orders = new ArrayList<Order>();
        Cursor c = db.rawQuery("select * from OVERORDER",null);
        while (c.moveToNext()) {
            Order order = new Order();
            order.setTaskId(c.getInt(c.getColumnIndex("taskId")));
            order.setLocation(c.getString(c.getColumnIndex("location")));
            String myDate=c.getString(c.getColumnIndex("time"));
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            try {
                order.setDoneTime(format.parse(myDate));
            }catch (Exception e){
                e.printStackTrace();
            }
            orders.add(order);
        }
        c.close();
        return orders;
    }

    /**
     * 查询一个订单的详情
     * @param taskId
     * @return
     */
    public Order queryOrderDetail(int taskId){
        Order order = new Order();
        Cursor c = db.rawQuery("select * from OVERORDER where taskId=?",new String[]{taskId+""});
        if (c.moveToNext()) {
            order.setTaskId(c.getInt(c.getColumnIndex("taskId")));
            order.setBilling(c.getInt(c.getColumnIndex("billing")));
            order.set_id(c.getString(c.getColumnIndex("_id")));
            order.set_rev(c.getString(c.getColumnIndex("_rev")));
            order.setGuestId(c.getString(c.getColumnIndex("guestId")));
            order.setLocation(c.getString(c.getColumnIndex("location")));
            String myDate=c.getString(c.getColumnIndex("time"));
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            try {
                order.setDoneTime(format.parse(myDate));
            }catch (Exception e){
                e.printStackTrace();
            }
            order.setAlipayOrderId(c.getString(c.getColumnIndex("alipayOrderId")));
            order.setStatus(c.getInt(c.getColumnIndex("status")));
            order.setDiscount(c.getInt(c.getColumnIndex("discount")));
            order.setProcessDefId(c.getString(c.getColumnIndex("processDefId")));
            order.setGuestPassport(c.getString(c.getColumnIndex("guestPassport")));
            order.setSeatLat(c.getString(c.getColumnIndex("seatLat")));
            order.setSeatLng(c.getString(c.getColumnIndex("seatLng")));
            order.setRejected((c.getInt(c.getColumnIndex("rejected")) == 1 ? true : false));

            List<Food> foods=new ArrayList<Food>();
            Cursor fc = db.rawQuery("select * from FOOD where taskId=?",new String[]{taskId+""});
            while (fc.moveToNext()) {
                Food f=new Food();
                f.setCount(fc.getInt(fc.getColumnIndex("count")));
                f.setName(fc.getString(fc.getColumnIndex("name")));
                f.setPrice(fc.getDouble(fc.getColumnIndex("price")));
                foods.add(f);
            }
            fc.close();
            order.setFoods(foods);
        }
        c.close();
        return order;
    }


}
