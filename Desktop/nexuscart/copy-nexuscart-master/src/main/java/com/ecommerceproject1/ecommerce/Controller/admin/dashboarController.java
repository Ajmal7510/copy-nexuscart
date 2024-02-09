package com.ecommerceproject1.ecommerce.Controller.admin;

import com.ecommerceproject1.ecommerce.Entity.user.Orders;
import com.ecommerceproject1.ecommerce.Service.Admin.DashboardService;
import com.ecommerceproject1.ecommerce.Service.User.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Arrays;
import java.util.List;


@Controller
@RequestMapping("/admin/dashboard")
public class dashboarController {

@Autowired
private DashboardService dashboardService;

@Autowired
private OrderService orderService;
    @GetMapping("")
    public String getdashboard(Model model){

        List<Orders> orders = dashboardService.getAllOrders();
        double totalRevenue = dashboardService.calculateTotalRevenue();
        long totalUserCount = dashboardService.getTotalUserCount();
        long totalProductCount = dashboardService.getTotalProductCount();
        // Add data to the model for Thymeleaf to render
        model.addAttribute("totalOrder", orders.size());
        model.addAttribute("totalRevenue", totalRevenue);
        model.addAttribute("totalUserCount", totalUserCount);
        model.addAttribute("totalProduct", totalProductCount);
        // Simulating data from the backend model
        List<String> labels = Arrays.asList("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat");
        List<Double> weeklyArray = orderService.getSalesAmountForLast7Days();
        System.out.println(" weekly report"+ weeklyArray);
        // Adding data to the Thymeleaf model
        model.addAttribute("labels", labels);
        model.addAttribute("weeklyArray", weeklyArray);
        return "admin/dashboard";
    }


}

