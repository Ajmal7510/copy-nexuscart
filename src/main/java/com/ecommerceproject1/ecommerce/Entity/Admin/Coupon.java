package com.ecommerceproject1.ecommerce.Entity.Admin;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "coupons_sequence")
    @SequenceGenerator(name = "coupons_sequence", sequenceName = "coupons_sequence", allocationSize = 1)
    private Long couponId;

    private String couponName;

    @Column(unique = true, nullable = false)
    private String couponCode;

    @Column(nullable = false)
    private int discountPercentage;

    @Column(nullable = false)
    private LocalDate expirationDate;


    private double minimumAmount;

    @Column(nullable = false)
    private boolean isActive = true;

    public boolean isValid() {
        LocalDate currentDate = LocalDate.now();

        // Check if the coupon is active
        if (!isActive) {
            return false;
        }

        // Check if the current date is before or equal to the expiration date
        if(expirationDate != null && currentDate.isAfter(expirationDate)) {
            return false;
        }

        return true;
    }




}
