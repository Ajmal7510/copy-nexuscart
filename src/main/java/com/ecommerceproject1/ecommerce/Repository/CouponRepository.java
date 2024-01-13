package com.ecommerceproject1.ecommerce.Repository;

import com.ecommerceproject1.ecommerce.Entity.Admin.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CouponRepository extends JpaRepository<Coupon,Long> {
    Coupon findByCouponCode(String copenCode);
    List<Coupon> findByIsActiveAndExpirationDateAfter(boolean isActive, LocalDate currentDate);


}
