package com.ecommerceproject1.ecommerce.Service.Admin;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public interface AdminOrderService {

    String Orders(Model model);
    public ResponseEntity<String> updateOrderStatus(Long orderId, String status);
    String orderDeatails(Long orderId,Model model);
}
