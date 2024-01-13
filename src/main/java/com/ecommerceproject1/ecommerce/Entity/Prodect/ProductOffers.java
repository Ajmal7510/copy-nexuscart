package com.ecommerceproject1.ecommerce.Entity.Prodect;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductOffers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long offerId;
    private Float discountAmount;
    @OneToOne
    @JoinColumn(name = "product_id")
    private Products products;
}
