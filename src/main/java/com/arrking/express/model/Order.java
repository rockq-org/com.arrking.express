package com.arrking.express.model;
import  java.util.List;
/**
 * Created by Jim on 2015/1/17.
 */
public class Order {

    private long _id;
    private long _rev;
    private int state;
    private double billing;
    private double discount;
    private List<Food> foods;
    private long guestId;
    private String guestPassport;
    private double seatLng;
    private double seatLat;
    private boolean rejected;
    private long alipayOrderId;

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public long get_rev() {
        return _rev;
    }

    public void set_rev(long _rev) {
        this._rev = _rev;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public double getBilling() {
        return billing;
    }

    public void setBilling(double billing) {
        this.billing = billing;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public List<Food> getFoods() {
        return foods;
    }

    public void setFoods(List<Food> foods) {
        this.foods = foods;
    }

    public long getGuestId() {
        return guestId;
    }

    public void setGuestId(long guestId) {
        this.guestId = guestId;
    }

    public String getGuestPassport() {
        return guestPassport;
    }

    public void setGuestPassport(String guestPassport) {
        this.guestPassport = guestPassport;
    }

    public double getSeatLng() {
        return seatLng;
    }

    public void setSeatLng(double seatLng) {
        this.seatLng = seatLng;
    }

    public double getSeatLat() {
        return seatLat;
    }

    public void setSeatLat(double seatLat) {
        this.seatLat = seatLat;
    }

    public boolean isRejected() {
        return rejected;
    }

    public void setRejected(boolean rejected) {
        this.rejected = rejected;
    }

    public long getAlipayOrderId() {
        return alipayOrderId;
    }

    public void setAlipayOrderId(long alipayOrderId) {
        this.alipayOrderId = alipayOrderId;
    }
}
