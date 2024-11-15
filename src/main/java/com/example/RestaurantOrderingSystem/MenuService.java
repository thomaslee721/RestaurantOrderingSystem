package com.example.RestaurantOrderingSystem;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MenuService {
    private List<MenuItem> menuItems;

    // Constructor to initialize the menu
    public MenuService() {
        menuItems = new ArrayList<>();
        // Add example menu item(s) or load from database
        menuItems.add(new MenuItem(1L, "Example Dish", "This is a delicious example dish.", 9.99));
    }

    // Method to get all menu items
    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    // Method to add a new menu item
    public void addMenuItem(MenuItem item) {
        menuItems.add(item);
    }

    // Method to edit an existing menu item
    public void editMenuItem(Long id, String name, String description, double price) {
        for (MenuItem item : menuItems) {
            if (item.getId().equals(id)) {
                item.setName(name);
                item.setDescription(description);
                item.setPrice(price);
            }
        }
    }
}
