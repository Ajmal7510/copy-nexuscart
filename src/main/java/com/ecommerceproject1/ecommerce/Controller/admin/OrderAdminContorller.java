package com.ecommerceproject1.ecommerce.Controller.admin;

import com.ecommerceproject1.ecommerce.Service.Admin.AdminOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin/orders")
public class OrderAdminContorller {

    @Autowired
    private AdminOrderService adminOrderService;

    @GetMapping("")
    public String OrderList(Model model){
        return adminOrderService.Orders(model);
    }

    @PutMapping("/change-Orderstatus")
    @ResponseBody
    public ResponseEntity<String> updateOrderStatus(@RequestParam Long orderId, @RequestParam String newStatus) {
        return adminOrderService.updateOrderStatus(orderId,newStatus);
    }


    @GetMapping("/Details/{orderId}")
    public String orderDetails(@PathVariable Long orderId,Model model){
        return adminOrderService.orderDeatails(orderId,model);
    }



}
