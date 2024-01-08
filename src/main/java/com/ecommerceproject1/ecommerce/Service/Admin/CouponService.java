package com.ecommerceproject1.ecommerce.Service.Admin;

import com.ecommerceproject1.ecommerce.Entity.Admin.Coupon;
import com.ecommerceproject1.ecommerce.model.Admin.CouponDto;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Service
public interface CouponService {
    String getCoupen(Model model);
    String addcoupon(Coupon coupondeatails, RedirectAttributes red);

    String updateCoupon(CouponDto couponDto,RedirectAttributes red);
    String deleteCoupon(Long couponId,RedirectAttributes red);
    Coupon validateCoupon(String couponCode);


}
