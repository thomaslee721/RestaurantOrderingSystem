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
import java.util.List;


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

    @GetMapping("/viewMenu")
    public String viewMenu(Model model) {
        // Create an example menu item
        MenuItem exampleItem = new MenuItem();
        exampleItem.setId(1L); // Set a default ID
        exampleItem.setName("Example Dish");
        exampleItem.setDescription("This is a delicious example dish.");
        exampleItem.setPrice(9.99);
    
        // Add the example item to the model
        model.addAttribute("menuItems", List.of(exampleItem)); // Wrap it in a list
    
        return "viewMenu"; // Thymeleaf template for viewing menu
    }
            
}
