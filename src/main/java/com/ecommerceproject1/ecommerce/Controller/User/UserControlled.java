package com.ecommerceproject1.ecommerce.Controller.User;

import com.ecommerceproject1.ecommerce.Service.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserControlled {
    @Autowired
    private UserService userService;



    @GetMapping("/shop")
     public String shop(Model model) {
        return userService.shopPage(model);
    }

    @GetMapping("/shop/allproducts")
    public String allproduct(@RequestParam(name = "page", defaultValue = "0") int page,
                             @RequestParam(name = "size", defaultValue = "10") int size,
                             @RequestParam(value = "searchKey", required = false) String searchKey,
                       @RequestParam(value = "type", required = false) String type,
                       @RequestParam(value = "value", required = false) String value,
                       Model model) {
        return userService.allproduct(searchKey,model,type,value,page,size);
    }

    @GetMapping("product-details/{id}")
    public String productDeatails(Model model, @PathVariable("id") Long id){

        return userService.productdetails(model,id);
    }

    @PutMapping("/sample")
    public ResponseEntity<Map<String,Object>>sample(){

       System.out.println("put working");
       System.out.println("working form submit");
        Map<String,Object> response = new HashMap<>();
       response.put("success",true);
       return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/wallet")
    public String wallet(Model model){
        return userService.getwallet(model);
    }


    @GetMapping("/wishlist")
    public String wishlist(Model model){
        return userService.getwishlist(model);
    }



   @PutMapping("/wishlist/addProductToWishlist/{productId}")
    public ResponseEntity<String> addProductToWishlist(@PathVariable Long productId){

       try {
           userService.addProductToWishlist(productId);
           return ResponseEntity.ok("Banner activated successfully");
       } catch (Exception e) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
       }
   }

    @GetMapping("/wishlist/removeProduct/{productId}")
    public String removeProductFromWishlist(@PathVariable Long productId) {
        userService.removeProductFromWishlist(productId);
        return "redirect:/user/wishlist";

    }

}

