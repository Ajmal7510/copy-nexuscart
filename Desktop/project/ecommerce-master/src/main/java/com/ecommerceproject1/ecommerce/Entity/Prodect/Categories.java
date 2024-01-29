package com.ecommerceproject1.ecommerce.Entity.Prodect;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Categories {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cat_sequence")
    @SequenceGenerator(name = "cat_sequence", sequenceName = "cat_sequence", allocationSize = 1)
    private Long categoryId;
    private String categoryName;
    private boolean status = true;
}
