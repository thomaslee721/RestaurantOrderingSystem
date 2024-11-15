package com.example.RestaurantOrderingSystem;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HomeController {

    @Autowired
    private MenuService menuService; // Inject the MenuService

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
    public String admin() {
        return "admin"; // Redirects to admin page
    }

    @GetMapping("/order")
    public String order(@RequestParam("tableNumber") int tableNumber, Model model) {
        model.addAttribute("tableNumber", tableNumber);
        return "order"; // Redirects to order page for customers
    }

    // View menu page (list of items)
    @GetMapping("/viewMenu")
    public String viewMenu(Model model) {
        model.addAttribute("menuItems", menuService.getAllMenuItems());
        return "viewMenu"; // Thymeleaf template for viewing menu
    }    

    // Edit menu item page
    @GetMapping("/editMenu/{id}")
    public String editMenu(@PathVariable Long id, Model model) {
        MenuItem menuItem = menuService.getMenuItemById(id);
        if (menuItem != null) {
            model.addAttribute("menuItem", menuItem);
            return "editMenu"; // Template for editing menu item
        }
        return "redirect:/viewMenu"; // If item not found, redirect to viewMenu
    }

    // Save updated menu item
    @PostMapping("/editMenu")
    public String saveMenuItem(MenuItem menuItem) {
        menuService.saveOrUpdateMenuItem(menuItem);
        return "redirect:/viewMenu"; // Redirect back to the view menu page after saving
    }
        
}
