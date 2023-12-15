package com.ecommerceproject1.ecommerce.Entity.Prodect;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Offers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long offerId;
    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brands brands;
    private Float discountPec;
}
