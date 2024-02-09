package com.ecommerceproject1.ecommerce.Service.Admin;

import com.ecommerceproject1.ecommerce.Entity.user.Orders;
import com.ecommerceproject1.ecommerce.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class dashboardServiceImp implements DashboardService {
    @Autowired
    private OrderRepository orderRepository;
    public List<Orders> getAllOrders() {
        // Implement logic to fetch all orders from the database
        return orderRepository.findAll();
    }

    public double calculateTotalRevenue() {
        // Implement logic to calculate total revenue from orders
        return orderRepository.calculateTotalRevenue();
    }

    public long getTotalUserCount() {
        // Implement logic to fetch the total user count from the database
        return orderRepository.getTotalUserCount();
    }

    public long getTotalProductCount() {
        // Implement logic to fetch the total product count from the database
        return orderRepository.getTotalProductCount();
    }
}

