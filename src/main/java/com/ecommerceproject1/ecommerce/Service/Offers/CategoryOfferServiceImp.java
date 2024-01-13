package com.ecommerceproject1.ecommerce.Service.Offers;

import com.ecommerceproject1.ecommerce.Entity.Offers.CategoryOffer;
import com.ecommerceproject1.ecommerce.Entity.Prodect.Categories;
import com.ecommerceproject1.ecommerce.Repository.CategoryOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class CategoryOfferServiceImp implements CategoryOfferService {

    @Autowired
    private CategoryOfferRepository categoryOfferRepository;

    @Override
    public void createCategoryOffer(Categories category) {
        CategoryOffer categoryOffer=new CategoryOffer();
        categoryOffer.setCategory(category);
        LocalDate expirationDate = LocalDate.now().plus(30, ChronoUnit.DAYS);
        categoryOffer.setExpirationDate(expirationDate);
        categoryOffer.setDiscountAmount(0f);
        categoryOfferRepository.save(categoryOffer);
    }
}
