package com.arrking.express.model;
import  java.util.List;
/**
 * Created by Jim on 2015/1/17.
 */
public class Order {

    private String _id;
    private String _rev;
    private int status;
    private int billing;
    private int discount;
    private List<Food> foods;
    private String guestId;
    private String guestPassport;
    private String seatLng;
    private String seatLat;
    private String processDefId;
    private boolean rejected;
    private String alipayOrderId;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_rev() {
        return _rev;
    }

    public void set_rev(String _rev) {
        this._rev = _rev;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getBilling() {
        return billing;
    }

    public void setBilling(int billing) {
        this.billing = billing;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public List<Food> getFoods() {
        return foods;
    }

    public void setFoods(List<Food> foods) {
        this.foods = foods;
    }

    public String getGuestId() {
        return guestId;
    }

    public void setGuestId(String guestId) {
        this.guestId = guestId;
    }

    public String getGuestPassport() {
        return guestPassport;
    }

    public void setGuestPassport(String guestPassport) {
        this.guestPassport = guestPassport;
    }

    public String getSeatLng() {
        return seatLng;
    }

    public void setSeatLng(String seatLng) {
        this.seatLng = seatLng;
    }

    public String getSeatLat() {
        return seatLat;
    }

    public void setSeatLat(String seatLat) {
        this.seatLat = seatLat;
    }

    public String getProcessDefId() {
        return processDefId;
    }

    public void setProcessDefId(String processDefId) {
        this.processDefId = processDefId;
    }

    public String getAlipayOrderId() {
        return alipayOrderId;
    }

    public void setAlipayOrderId(String alipayOrderId) {
        this.alipayOrderId = alipayOrderId;
    }

    public boolean isRejected() {
        return rejected;
    }

    public void setRejected(boolean rejected) {
        this.rejected = rejected;
    }
}
