package com.example.dosirak_test2;

public class ThirdData {
    private String tv_name;
    private String tv_stock;
    private String where;

    //

    public ThirdData(String tv_name, String tv_stock, String where) {
        this.tv_name = tv_name;
        this.tv_stock = tv_stock;
        this.where = where;
    }

    public String getTv_name() { return tv_name; }
    public void setTv_name(String tv_name) {
        this.tv_name = tv_name;
    }

    public String getTv_stock() {
        return tv_stock;
    }
    public void setTv_stock(String tv_stock) {
        this.tv_stock = tv_stock;
    }

    public String getWhere() { return where; }
    public void setWhere(String where) {
        this.where = where;
    }
}
