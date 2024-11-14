package com.example.RestaurantOrderingSystem;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class HomeController {

    private final Map<String, String> tablePageMappings = new HashMap<>();

    public HomeController() {
        loadTableMappings();
    }

    private void loadTableMappings() {
        String csvFilePath = "src/main/resources/table_page_mappings.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length == 2) {
                    tablePageMappings.put(values[0], values[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/")
    public String showLoginPage() {
        return "index"; // This will display the login page (index.html)
    }

    @PostMapping("/login")
    public ModelAndView processLogin(@RequestParam("tableNumber") String tableNumber) {
        String page = tablePageMappings.get(tableNumber);
        if (page != null) {
            return new ModelAndView(new RedirectView("/" + page)); // Redirect to the specified page
        } else {
            ModelAndView modelAndView = new ModelAndView("index");
            modelAndView.addObject("error", "Invalid table number. Please try again.");
            return modelAndView;
        }
    }
}
