package com.ecommerceproject1.ecommerce.model.product;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class Product_DTO {




    private String productName;


    private String description;


    private Integer stock;


    private Float price;


    private Long categoryId;


    private Long brandId;

    private MultipartFile[] productImages;

    private String ram;
    private String storage;
}