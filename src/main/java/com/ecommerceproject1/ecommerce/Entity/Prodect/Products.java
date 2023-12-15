package com.ecommerceproject1.ecommerce.Entity.Prodect;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Products {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productID;

    private String productName;

    @Column(length = 512)
    private String description;

    private boolean isActive=true;

    private Integer stock;

    private Float price;

    @Column(name = "images", columnDefinition = "text[]")
    private String[] imagesPath = new String[3];

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Categories category;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brands brand;

    private boolean isDelete=false;

    @CreatedDate
    private LocalDateTime publishedAt;

}
