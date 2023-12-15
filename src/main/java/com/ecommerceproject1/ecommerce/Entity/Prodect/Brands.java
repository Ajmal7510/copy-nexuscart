package com.ecommerceproject1.ecommerce.Entity.Prodect;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Brands {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long brandId;
    private String brandName;
    private boolean status = true;

}
