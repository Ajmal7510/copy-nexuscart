package com.ecommerceproject1.ecommerce.Service.User;

import com.ecommerceproject1.ecommerce.Entity.user.Cart;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.security.Principal;

@Service
public interface CartService {


    Cart getcart();

    String cart(Model model, Principal principal);
    ResponseEntity<String> addtocar(Long productId);
     ResponseEntity<String> removeFromCart(Long productId);
    ResponseEntity<String> increaseProductQuantity(Long productId);
    ResponseEntity<String> decreaseProductQuantity(Long productId);
}
