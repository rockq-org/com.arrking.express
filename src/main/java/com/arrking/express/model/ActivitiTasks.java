package com.arrking.express.model;

import java.util.List;

/**
 * Created by hain on 08/01/2015.
 */
public class ActivitiTasks {

    private int total;
    private int start;
    private String sort;
    private String order;
    private int size;
    private List<ActivitiTask> data;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<ActivitiTask> getData() {
        return data;
    }

    public void setData(List<ActivitiTask> data) {
        this.data = data;
    }
}
