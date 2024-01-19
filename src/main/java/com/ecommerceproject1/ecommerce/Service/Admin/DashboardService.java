package com.ecommerceproject1.ecommerce.Service.Admin;

import com.ecommerceproject1.ecommerce.Entity.user.Orders;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DashboardService {
    public List<Orders> getAllOrders();


    public double calculateTotalRevenue();
    public long getTotalUserCount();

    public long getTotalProductCount();
}
