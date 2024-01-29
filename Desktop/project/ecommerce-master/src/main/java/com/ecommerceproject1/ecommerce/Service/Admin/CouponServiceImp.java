package com.ecommerceproject1.ecommerce.Service.Admin;

import com.ecommerceproject1.ecommerce.Entity.Admin.Coupon;
import com.ecommerceproject1.ecommerce.Repository.CouponRepository;
import com.ecommerceproject1.ecommerce.model.Admin.CouponDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Service
@Slf4j
public class CouponServiceImp implements CouponService {

      @Autowired
    private CouponRepository couponRepository;

    @Override
    public String getCoupen(Model model) {
        model.addAttribute("coupons",couponRepository.findAll());
        return "admin/coupon";
    }

    @Override
    public String addcoupon(Coupon coupondeatails, RedirectAttributes red) {
        try {
            Coupon coupon=new Coupon();

            coupon=coupondeatails;
       couponRepository.save(coupon);

       red.addFlashAttribute("success","coupon added success");

       return "redirect:/admin/coupon";

        }catch (Exception e) {
            log.info("add coupon"+e.getMessage());
            return "Eemption/404";
        }
    }

    @Override
    public String updateCoupon(CouponDto couponDto, RedirectAttributes red) {

        System.out.println(couponDto.getCouponId());
        try {
            Coupon coupon = couponRepository.findById(couponDto.getCouponId()).get();


                coupon.setCouponName(couponDto.getCouponName());
                coupon.setCouponCode(couponDto.getCouponCode());
                coupon.setExpirationDate(couponDto.getExpirationDate());
                coupon.setDiscountPercentage(couponDto.getDiscountPercentage());
                coupon.setMinimumAmount(couponDto.getMinimumAmount());

                couponRepository.save(coupon);


            red.addFlashAttribute("updateSuccess","update success ");
            return "redirect:/admin/coupon";
        } catch (Exception e) {
            log.info("editCoupon2"+e.getMessage());
            return "Exception/404";
        }
    }

    @Override
    public String deleteCoupon(Long couponId, RedirectAttributes red) {
        Coupon optionalCoupon = couponRepository.findById(couponId).get();


            couponRepository.delete(optionalCoupon);
            return "redirect:/admin/coupon";

    }

    @Override
    public Coupon validateCoupon(String couponCode) {
        Coupon coupon = couponRepository.findByCouponCode(couponCode);

        if (coupon != null && coupon.isValid()) {
            return coupon;
        }

        return null;
    }

    @Override
    public boolean findcoupon(String couponCode) {
        Coupon coupon = couponRepository.findByCouponCode(couponCode);
        System.out.println("Coupon: " + coupon);
        boolean t = coupon != null;
        System.out.println("Result: " + t);
        return t;
    }

}
