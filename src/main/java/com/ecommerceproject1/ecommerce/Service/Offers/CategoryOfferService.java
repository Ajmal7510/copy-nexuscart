package com.ecommerceproject1.ecommerce.Service.Offers;

import com.ecommerceproject1.ecommerce.Entity.Prodect.Categories;
import org.springframework.stereotype.Service;

@Service
public interface CategoryOfferService {
    void createCategoryOffer(Categories category);
}
