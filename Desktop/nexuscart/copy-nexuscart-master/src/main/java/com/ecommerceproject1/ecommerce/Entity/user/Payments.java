package com.ecommerceproject1.ecommerce.Entity.user;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Payments {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payment_sequence")
    @SequenceGenerator(name = "payment_sequence", sequenceName = "payment_sequence", allocationSize = 1)
    @Column(name = "paymentId")
    private Integer paymentId;

    @Column(name = "paymentMethod")
    private String paymentMethod;

    @OneToOne
    private Orders orders;

    @Column(name = "status")
    private String status;

    @Column(name = "paymentTime")
    private String paymentTime;

    @Column(name = "amount")
    private double amount;

}
