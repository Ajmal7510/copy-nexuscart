package com.ecommerceproject1.ecommerce.Service.User;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public interface OrderService {

    String checkOut(String coupen,Model model);
   ResponseEntity<Boolean> checkOutValidation(Model model);

   String orderitem(Long addressId,String payment,String order);

   String myOrders(Model model);

   String orderDetails(Long productId,Model model);

   String cancelOrder(Long orderId);

   String returnOrder(Long orderId);


}
