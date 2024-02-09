package com.ecommerceproject1.ecommerce.Controller.admin;

import com.ecommerceproject1.ecommerce.Entity.Admin.Coupon;
import com.ecommerceproject1.ecommerce.Service.Admin.CouponService;
import com.ecommerceproject1.ecommerce.model.Admin.CouponDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/admin/coupon")
public class CouponController {

    @Autowired
    private CouponService couponService;
    @GetMapping("")
    public String coupen(Model model){
        return couponService.getCoupen(model);
    }

    @PostMapping("/add-coupon")
    public String coupenAdd(@ModelAttribute Coupon coupondetails, RedirectAttributes red){

        return couponService.addcoupon(coupondetails,red);

    }

    @PostMapping("/updateCoupon")
    public String updateCoupon(@ModelAttribute CouponDto couponDeatils, RedirectAttributes red){
        System.out.println("its working");
        return couponService.updateCoupon(couponDeatils,red);

    }

    @GetMapping("/deleteCoupon/{id}")
    public String deleteCoupon(@PathVariable Long id,RedirectAttributes red) {


        return couponService.deleteCoupon(id,red);

    }
    @GetMapping("/checkCouponCode/{couponCode}")
    @ResponseBody
    public boolean checkCouponCode(@PathVariable String couponCode) {

        boolean t=couponService.findcoupon(couponCode);
        System.out.println(t);

        return true;
    }


}
