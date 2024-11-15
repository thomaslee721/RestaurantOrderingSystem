package com.example.RestaurantOrderingSystem;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
public class HomeController {

    // Example Menu Item
    private List<MenuItem> getDefaultMenuItems() {
        MenuItem exampleItem = new MenuItem(1L, "Example Dish", "This is a delicious example dish.", 9.99);
        return List.of(exampleItem); // Return a list containing the example item
    }

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
        model.addAttribute("menuItems", getDefaultMenuItems()); // Pass the menu items to the admin page
        return "admin"; // Redirects to admin page
    }

    @GetMapping("/order")
    public String order(@RequestParam("tableNumber") int tableNumber, Model model) {
        model.addAttribute("tableNumber", tableNumber);
        model.addAttribute("menuItems", getDefaultMenuItems()); // Pass the menu items to the order page
        return "order"; // Redirects to order page for customers
    }
    
    @GetMapping("/viewMenu")
    public String viewMenu(Model model) {
        model.addAttribute("menuItems", getDefaultMenuItems()); // Pass the menu items to the viewMenu page
        return "viewMenu"; // Thymeleaf template for viewing menu
    }
}
