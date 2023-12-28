package com.ecommerceproject1.ecommerce.Controller.User;

import com.ecommerceproject1.ecommerce.Service.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String shopAllProduct(Model model){
        return userService.allproduct(model);
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




}

