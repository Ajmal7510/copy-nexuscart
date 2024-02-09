package com.ecommerceproject1.ecommerce.Entity.Offers;

import com.ecommerceproject1.ecommerce.Entity.Prodect.Categories;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryOffer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Float discountAmount;

    // Other fields related to the offer
    private LocalDate expirationDate;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Categories category;
}
