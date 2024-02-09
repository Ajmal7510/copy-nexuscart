package com.ecommerceproject1.ecommerce.Entity.user;

import com.ecommerceproject1.ecommerce.Entity.Prodect.Products;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    private Products product;
    //    private double productPriceWhenOrdering;
    private Integer quantity ;

    @ManyToOne()
    private Orders orders;


    public OrderItem(CartProduct cartItem) {
        this.product = cartItem.getProduct();
        this.quantity = cartItem.getQuantity();

    }


}
