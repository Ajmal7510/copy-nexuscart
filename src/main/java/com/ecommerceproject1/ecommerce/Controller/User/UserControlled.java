package com.ecommerceproject1.ecommerce.Controller.User;

import com.ecommerceproject1.ecommerce.Service.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

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
    @GetMapping("/profile")
    public String userProfile(Principal principal,Model model){
        model.addAttribute("userData",userService.userInfofindByEmail(principal.getName()));
        return "user/Profile";
    }

}

