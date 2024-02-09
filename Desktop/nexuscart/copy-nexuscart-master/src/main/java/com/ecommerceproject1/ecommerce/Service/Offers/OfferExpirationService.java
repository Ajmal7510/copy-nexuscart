package com.ecommerceproject1.ecommerce.Service.Offers;

import com.ecommerceproject1.ecommerce.Entity.Offers.CategoryOffer;
import com.ecommerceproject1.ecommerce.Entity.Prodect.Products;
import com.ecommerceproject1.ecommerce.Repository.CategoryOfferRepository;
import com.ecommerceproject1.ecommerce.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class OfferExpirationService {

    @Autowired
    private CategoryOfferRepository categoryOfferRepository;
    @Autowired
    private ProductRepository productRepository;

    @Scheduled(cron = "0/100 * * * * *") // Runs every 10 seconds
    public void checkAndExpireOffers() {
        System.out.println("its working");
        LocalDate currentDate = LocalDate.now();
        List<CategoryOffer> expiredOffers = categoryOfferRepository.findByExpirationDateBefore(currentDate);

        for (CategoryOffer offer : expiredOffers) {
            List<Products> products=productRepository.findByCategory(offer.getCategory());
            for(Products product:products){

                Float price=product.getPrice()+offer.getDiscountAmount();
                product.setPrice(price);
                productRepository.save(product);
            }

            offer.setDiscountAmount(0f);
            categoryOfferRepository.save(offer);


        }
    }
}
