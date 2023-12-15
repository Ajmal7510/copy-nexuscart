package com.ecommerceproject1.ecommerce.Entity.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private String role;
    private boolean isEnabled=true;
    @OneToOne
    @JoinColumn(name = "user_cart")
    private Cart cart;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private List<Address> userAddresses = new LinkedList<>();
    private String profilePhoto="img.png";
}
