package com.ecommerceproject1.ecommerce.Service.Admin;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.Date;

@Service
public interface OffersService {
    String viewOfferspage(Model model);
    String addProductOffer(Long productId,Float discountAmout);

    void deleteProductOfferById(Long id);

    String addCategoryOffer(Long categoryId, Float discountAmount, LocalDate expireDate);

    void deleteCategoryOffer(Long id);
}
