package com.ecommerceproject1.ecommerce.Entity.Prodect;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductOffer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long offerId;



    private Float discountAmount;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @OneToOne
    @JoinColumn()
    private Products products;

}
