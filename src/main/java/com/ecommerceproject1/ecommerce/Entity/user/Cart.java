package com.ecommerceproject1.ecommerce.Entity.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cart_sequence")
    @SequenceGenerator(name = "cart_sequence", sequenceName = "cart_sequence", allocationSize = 1)
    private Long CartId;
    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "cart_items")
    private List<CartProduct> cartProducts;
    @OneToOne
    @JoinColumn(name = "cart_user_id" )
    private UserInfo user;
    private double totalCartAmount = 0.0;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cart cart = (Cart) o;
        return Objects.equals(CartId, cart.CartId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(CartId);
    }
}
