package com.ecommerceproject1.ecommerce.Controller.User;

import com.ecommerceproject1.ecommerce.Service.User.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/user/cart")
public class CartController {

    @Autowired
    CartService cartService;

    @GetMapping("")
    public String cart(Model model, Principal principal){
        return cartService.cart(model,principal);
    }


    @PostMapping("add-to-cart")
    public ResponseEntity<String> addToCart(@RequestParam("productId")Long prductId) {

        return cartService.addtocar(prductId);
    }
    @DeleteMapping("removeFromCart")
    public ResponseEntity<String> removeFromCart(@RequestParam("productId")Long productId){
        return cartService.removeFromCart(productId);
    }

    @PatchMapping("increasingQuantity")
    public ResponseEntity<String> incresingQuantity(@RequestParam("productId")Long productId){
        System.out.println("increasing");
        return cartService.increaseProductQuantity(productId);
    }

    @PatchMapping("decreasingQuantity")
    public ResponseEntity<String> decreasingQuantity(@RequestParam("productId")Long productId){
        System.out.println("decreasing");
        return cartService.decreaseProductQuantity(productId);
    }



}
