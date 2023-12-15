package com.ecommerceproject1.ecommerce.Entity.user;

import com.ecommerceproject1.ecommerce.Entity.Prodect.Products;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    private Cart cart;

    @ManyToOne
    private Products product;

    @Column(name = " quantity")
    private int quantity=1;

}
