package com.example.RestaurantOrderingSystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
public class HomeController {

    @Autowired
    private MenuService menuService; // Inject MenuService

    private static final String ORDERS_CSV_FILE_PATH = "src/main/resources/orders.csv";
    private static final AtomicInteger orderCounter = new AtomicInteger(1); // Atomic counter for order numbers

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
        return "order"; // Thymeleaf template for ordering
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

    @GetMapping("/viewOrders")
    public String viewOrders(Model model) {
        model.addAttribute("orders", getOrders());
        return "viewOrder";
    }

    @PostMapping("/updateOrderStatus")
    public String updateOrderStatus(@RequestParam("orderNo") int orderNo, @RequestParam("status") String status) {
        List<Order> orders = getOrders();
        for (Order order : orders) {
            if (order.getOrderNo() == orderNo) {
                order.setStatus(status);
                break;
            }
        }
        saveOrders(orders);
        return "redirect:/viewOrders";
    }

    @PostMapping("/placeOrder")
    @ResponseBody
    public String placeOrder(@RequestBody OrderRequest orderRequest) {
        List<Order> orders = getOrders();
        int nextOrderNo = getNextOrderNo(orders);
        String formattedOrderNo = String.format("%02d", nextOrderNo);
        String formattedTableNo = String.format("%02d", orderRequest.getTableNumber());

        Order newOrder = new Order(Integer.parseInt(formattedOrderNo), Integer.parseInt(formattedTableNo), "Confirmed", orderRequest.getTotal());
        orders.add(newOrder);
        saveOrders(orders);
        return "Order placed successfully";
    }

    private int getNextOrderNo(List<Order> orders) {
        int maxOrderNo = orders.stream().mapToInt(Order::getOrderNo).max().orElse(0);
        return (maxOrderNo % 99) + 1;
    }

    private List<Order> getOrders() {
        List<Order> orders = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ORDERS_CSV_FILE_PATH))) {
            String line;
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false; // Skip header line
                    continue;
                }
                String[] values = line.split(",");
                if (values.length == 4) {
                    orders.add(new Order(Integer.parseInt(values[0]), Integer.parseInt(values[1]), values[2], Double.parseDouble(values[3])));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return orders;
    }

    private void saveOrders(List<Order> orders) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ORDERS_CSV_FILE_PATH))) {
            bw.write("Order No.,Table No.,Status,Total");
            bw.newLine();
            for (Order order : orders) {
                bw.write(String.format("%02d,%02d,%s,%.2f", order.getOrderNo(), order.getTableNo(), order.getStatus(), order.getTotal()));
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
