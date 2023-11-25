package com.ecommerceproject1.ecommerce.Controller;

import com.ecommerceproject1.ecommerce.Entity.UserInfo;
import com.ecommerceproject1.ecommerce.Service.User.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {
    @Autowired
    private UserInfoService userInfoService;


  @GetMapping("/login")
  public String login(){
      return "login";
  }
   @GetMapping("/signup")
   public String signuppage(){

        return "signup";
   }


   @PostMapping("/signup")
   public String signup(
           @ModelAttribute UserInfo user
   ){
       userInfoService.saveuser(user);
       return "redirect:/login?success";
   }





}
