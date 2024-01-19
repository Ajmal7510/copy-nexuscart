package com.ecommerceproject1.ecommerce.model.product;

import lombok.Data;

import java.time.LocalDate;
@Data
public class OrderAmountDto {
    private LocalDate orderDate;
    private Double totalAmount;
    public OrderAmountDto(LocalDate orderDate, Double totalAmount) {
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
    }
}
