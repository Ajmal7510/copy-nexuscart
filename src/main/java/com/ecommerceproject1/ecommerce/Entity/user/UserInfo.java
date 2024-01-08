package com.ecommerceproject1.ecommerce.Entity.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

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
    @OneToOne
    @JoinColumn(name = "user_wishlist")
    private  Wishlist wishlist;

    @OneToMany(mappedBy = "user")
    private List<Orders> orders;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Wallet wallet;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserInfo userInfo = (UserInfo) o;
        return Objects.equals(userId, userInfo.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }

}