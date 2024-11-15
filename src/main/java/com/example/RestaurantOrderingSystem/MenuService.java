package com.example.RestaurantOrderingSystem;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class MenuService {

    private List<MenuItem> menuItems = new ArrayList<>();

    // Default constructor to add example items if menu is empty
    public MenuService() {
        if (menuItems.isEmpty()) {
            // Add default example items
            menuItems.add(new MenuItem(1L, "Pizza", "Delicious cheese pizza", 9.99));
            menuItems.add(new MenuItem(2L, "Burger", "Juicy beef burger", 7.49));
            menuItems.add(new MenuItem(3L, "Pasta", "Creamy Alfredo pasta", 8.99));
        }
    }

    // Fetch all menu items
    public List<MenuItem> getAllMenuItems() {
        return menuItems;
    }

    // Fetch a menu item by its ID
    public MenuItem getMenuItemById(Long id) {
        return menuItems.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // Save or update menu item
    public void saveOrUpdateMenuItem(MenuItem menuItem) {
        // If the item already exists, update it; otherwise, add a new one
        menuItems.removeIf(item -> item.getId().equals(menuItem.getId()));
        menuItems.add(menuItem);
    }
}
