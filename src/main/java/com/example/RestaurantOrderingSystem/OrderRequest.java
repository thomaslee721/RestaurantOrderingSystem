package com.example.RestaurantOrderingSystem;

import java.util.Map;

public class OrderRequest {
    private int tableNumber;
    private Map<Long, CartItem> cart;
    private double total;

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public Map<Long, CartItem> getCart() {
        return cart;
    }

    public void setCart(Map<Long, CartItem> cart) {
        this.cart = cart;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public static class CartItem {
        private String name;
        private int amount;
        private double price;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }
    }
}
