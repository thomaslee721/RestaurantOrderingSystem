package com.example.RestaurantOrderingSystem;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class MenuService {
    
    // Sample data for now (this will be replaced by database calls later)
    private List<MenuItem> menuItems = new ArrayList<>();

    // Constructor to initialize with some sample menu items
    public MenuService() {
        menuItems.add(new MenuItem(1L, "Burger", "Juicy beef burger", 8.99));
        menuItems.add(new MenuItem(2L, "Pizza", "Cheese pizza with pepperoni", 12.99));
        menuItems.add(new MenuItem(3L, "Pasta", "Spaghetti with marinara sauce", 10.99));
    }

    // Get all menu items
    public List<MenuItem> getAllMenuItems() {
        return menuItems;
    }

    // Get a specific menu item by its ID
    public MenuItem getMenuItemById(Long id) {
        for (MenuItem item : menuItems) {
            if (item.getId().equals(id)) {
                return item;
            }
        }
        return null; // If not found
    }

    // Save or update a menu item (in this case, just modifying the list for now)
    public void saveOrUpdateMenuItem(MenuItem menuItem) {
        for (int i = 0; i < menuItems.size(); i++) {
            if (menuItems.get(i).getId().equals(menuItem.getId())) {
                menuItems.set(i, menuItem); // Update existing item
                return;
            }
        }
        menuItems.add(menuItem); // Add new item if it doesn't exist
    }
}
