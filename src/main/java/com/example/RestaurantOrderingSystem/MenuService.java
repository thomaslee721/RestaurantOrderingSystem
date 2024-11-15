package com.example.RestaurantOrderingSystem;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class MenuService {
    private List<MenuItem> menuItems = new ArrayList<>();

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    public void addMenuItem(MenuItem item) {
        menuItems.add(item);
    }

    public void updateMenuItem(int index, MenuItem item) {
        menuItems.set(index, item);
    }

    public void removeMenuItem(int index) {
        menuItems.remove(index);
    }
}
