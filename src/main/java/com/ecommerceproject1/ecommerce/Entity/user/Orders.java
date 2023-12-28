package com.ecommerceproject1.ecommerce.Entity.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Orders {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ORDER_SEQ")
    @SequenceGenerator(name="ORDER_SEQ", sequenceName="ORDER_SEQ", allocationSize=999)
    private Long orderId;


    @OneToMany
    private List<OrderItem> orderProducts;




    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;




    @Column(name = "orderDate")
    private LocalDate orderDate;
    @ManyToOne
    @JoinColumn(name = "user_id") // Adjust the column name as per your database schema
    private UserInfo user;


    @Column(name = "amount")
    private double totalAmount;

    @OneToOne(mappedBy = "orders", cascade = {CascadeType.ALL},orphanRemoval = true)
    private Payments payments;

    @Column(name = "status")
    private String status = "ordered";

    @Column(name = "amountStatus")
    private String amountStatus;

    @Column(name = "delivereDate")
    private LocalDate deliveredDate;

    @Column(name = "returnExpiryDate")
    private LocalDate returnExpiryDate;

    @Column(name = "cancelled")
    private boolean cancelled = false;

    @Column(name = "refundStatus")
    private boolean refundStatus = false;


}
