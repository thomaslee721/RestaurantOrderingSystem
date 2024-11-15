package com.example.RestaurantOrderingSystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private MenuService menuService; // Inject MenuService

    @GetMapping("/")
    public String home() {
        return "index"; // Displays the login page
    }

    @PostMapping("/logout")
    public RedirectView logout() {
        return new RedirectView("/");
    }

    @PostMapping("/login")
    public String login(@RequestParam("tableNumber") String tableNumber, Model model) {
        if (tableNumber.equalsIgnoreCase("admin")) {
            return "redirect:/admin";
        }
        try {
            int tableNum = Integer.parseInt(tableNumber);
            if (tableNum >= 0 && tableNum <= 99) {
                return "redirect:/order?tableNumber=" + tableNum; // Append the table number for redirection
            }
        } catch (NumberFormatException e) {
            // Handle invalid input
        }
        model.addAttribute("error", "Invalid table number or input.");
        return "index"; // Reloads the login page with an error message
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("menuItems", menuService.getMenuItems()); // Get menu items from MenuService
        return "admin"; // Redirects to admin page
    }

    @GetMapping("/order")
    public String order(@RequestParam("tableNumber") int tableNumber, Model model) {
        model.addAttribute("tableNumber", tableNumber);
        model.addAttribute("menuItems", menuService.getMenuItems()); // Get menu items from MenuService
        return "order"; // Redirects to order page for customers
    }

    @GetMapping("/viewMenu")
    public String viewMenu(Model model) {
        model.addAttribute("menuItems", menuService.getMenuItems()); // Get menu items from MenuService
        return "viewMenu"; // Thymeleaf template for viewing menu
    }

    @PostMapping("/addMenuItem")
    public String addMenuItem(@RequestParam String name, @RequestParam String description, @RequestParam double price) {
        MenuItem newItem = new MenuItem((long) (menuService.getMenuItems().size() + 1), name, description, price);
        menuService.addMenuItem(newItem);
        return "redirect:/viewMenu"; // Redirect to view the updated menu
    }

    @PostMapping("/deleteMenuItem")
    public String deleteMenuItem(@RequestParam Long id) {
        menuService.deleteMenuItem(id);
        return "redirect:/viewMenu"; // Redirect to view menu after deletion
    }

    @PostMapping("/saveMenuChanges")
    public String saveMenuChanges() {
        menuService.saveMenuChanges();
        return "redirect:/viewMenu"; // Redirect to view menu after saving changes
    }

}
