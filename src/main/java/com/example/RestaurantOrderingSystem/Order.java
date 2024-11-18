package com.example.RestaurantOrderingSystem;

public class Order {
    private int orderNo;
    private int tableNo;
    private String status;
    private double total;

    public Order(int orderNo, int tableNo, String status, double total) {
        this.orderNo = orderNo;
        this.tableNo = tableNo;
        this.status = status;
        this.total = total;
    }

    public int getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }

    public int getTableNo() {
        return tableNo;
    }

    public void setTableNo(int tableNo) {
        this.tableNo = tableNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
