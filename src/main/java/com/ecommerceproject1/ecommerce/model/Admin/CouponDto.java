package com.ecommerceproject1.ecommerce.model.Admin;

import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CouponDto {
    private Long couponId;

    private String couponName;

    private String couponCode;

    private int discountPercentage;

    private LocalDate expirationDate;

    private double minimumAmount;

}
