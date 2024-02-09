package com.ecommerceproject1.ecommerce.Entity.Admin;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Banner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bannerId;

    private String description;

    private String[] imagesPath = new String[3];

    private boolean isActive=false;


}
