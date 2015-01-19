package com.arrking.express.model;

import java.util.Date;

/**
 * Created by Jim on 2015/1/20.
 */
public class DBOrder {

    private int id;
    private Date date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
