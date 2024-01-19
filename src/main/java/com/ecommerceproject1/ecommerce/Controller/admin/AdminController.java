package com.ecommerceproject1.ecommerce.Controller.admin;

import com.ecommerceproject1.ecommerce.Entity.user.UserInfo;
import com.ecommerceproject1.ecommerce.Service.User.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserInfoService userInfoService;



    @GetMapping("/userlist")
    public String home(Model model){
        List<UserInfo> user=userInfoService.findByRole("USER");
        model.addAttribute("users",user);
        return "admin/userlist";
    }

    @PostMapping("/home/block")
    public String bock(@RequestParam Long userId , @RequestParam boolean isEnabled){
        userInfoService.updateUserEnabledStatus(userId, isEnabled);
        return "redirect:/admin/userlist";
    }







}
