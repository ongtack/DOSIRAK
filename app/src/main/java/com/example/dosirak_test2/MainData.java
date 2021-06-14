package com.example.dosirak_test2;

public class MainData {
    private String tv_name;
    private String tv_type;
    private String tv_price;
    private String tv_stock;
    private String barcede;

    public MainData(String tv_name, String tv_type, String tv_price, String tv_stock, String barcode) {
        this.tv_name = tv_name;
        this.tv_type = tv_type;
        this.tv_price = tv_price;
        this.tv_stock = tv_stock;
        this.barcede = barcode;
    }

    public String getTv_name() {
        return tv_name;
    }

    public void setTv_name(String tv_name) {
        this.tv_name = tv_name;
    }

    public String getTv_type() {
        return tv_type;
    }

    public void setTv_type(String tv_type) {
        this.tv_type = tv_type;
    }

    public String getTv_price() {
        return tv_price;
    }

    public void setTv_price(String tv_price) {
        this.tv_price = tv_price;
    }

    public String getTv_stock() {
        return tv_stock;
    }

    public void setTv_stock(String tv_stock) {
        this.tv_stock = tv_stock;
    }

    public String getBarcede() {
        return barcede;
    }
}
