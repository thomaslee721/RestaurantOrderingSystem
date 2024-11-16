package com.example.RestaurantOrderingSystem;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import org.springframework.stereotype.Service;
import com.opencsv.exceptions.CsvValidationException;

import java.io.InputStreamReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class MenuService {
    private static final String CSV_FILE_PATH = "menuItems.csv";

    // Method to get all menu items
    public List<MenuItem> getMenuItems() {
        List<MenuItem> menuItems = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(CSV_FILE_PATH)))) {
            String[] line;
            while ((line = reader.readNext()) != null) {
                MenuItem item = new MenuItem(Long.parseLong(line[0]), line[1], line[2], Double.parseDouble(line[3]));
                menuItems.add(item);
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
        return menuItems;
    }

    // Method to add a new menu item
    public void addMenuItem(MenuItem item) {
        List<MenuItem> menuItems = getMenuItems();
        menuItems.add(item);
        writeMenuItemsToCSV(menuItems);
    }

    // Method to edit an existing menu item
    public void editMenuItem(Long id, String name, String description, double price) {
        List<MenuItem> menuItems = getMenuItems();
        for (MenuItem item : menuItems) {
            if (item.getId().equals(id)) {
                item.setName(name);
                item.setDescription(description);
                item.setPrice(price);
            }
        }
        writeMenuItemsToCSV(menuItems);
    }

    // Method to delete a menu item by ID
    public void deleteMenuItem(Long id) {
        List<MenuItem> menuItems = getMenuItems();
        menuItems.removeIf(item -> item.getId().equals(id));
        writeMenuItemsToCSV(menuItems);
    }

    // Helper method to write menu items to CSV
    private void writeMenuItemsToCSV(List<MenuItem> menuItems) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(getClass().getClassLoader().getResource(CSV_FILE_PATH).getFile()))) {
            for (MenuItem item : menuItems) {
                String[] line = {item.getId().toString(), item.getName(), item.getDescription(), String.valueOf(item.getPrice())};
                writer.writeNext(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveMenuChanges() {
        List<MenuItem> menuItems = getMenuItems();
        writeMenuItemsToCSV(menuItems);
    }
}
