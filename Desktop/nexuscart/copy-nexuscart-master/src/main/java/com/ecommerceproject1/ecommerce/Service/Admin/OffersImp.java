package com.ecommerceproject1.ecommerce.Service.Admin;

import com.ecommerceproject1.ecommerce.Entity.Offers.CategoryOffer;
import com.ecommerceproject1.ecommerce.Entity.Prodect.Products;
import com.ecommerceproject1.ecommerce.Repository.CategoryOfferRepository;
import com.ecommerceproject1.ecommerce.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class OffersImp implements OffersService{
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryOfferRepository categoryOfferRepository;

    @Override
    public String viewOfferspage(Model model) {
        model.addAttribute("products",productRepository.findAllByIsActiveTrueAndIsDeleteFalse());
        model.addAttribute("categoryOffers",categoryOfferRepository.findAll());
        return "admin/Offerse-page";
    }

    @Override
    public String addProductOffer(Long productId, Float discountAmout) {

        Products products=productRepository.findById(productId).orElse(null);
        products.setProductDiscountAmout(discountAmout);
        Float productPrice=products.getPrice()-discountAmout;
        products.setPrice(productPrice);
        productRepository.save(products);
        return "redirect:/admin/offers";
    }

    @Override
    public void deleteProductOfferById(Long id) {
        Products products=productRepository.findById(id).orElse(null);
        products.setPrice(products.getPrice()+products.getProductDiscountAmout());
        products.setProductDiscountAmout(0f);
        productRepository.save(products);
    }

    @Override
    public String addCategoryOffer(Long categoryId, Float discountAmount, LocalDate expireDate) {
        CategoryOffer categoryOffer=categoryOfferRepository.findById(categoryId).orElse(null);
        categoryOffer.setDiscountAmount(discountAmount);
        categoryOffer.setExpirationDate(expireDate);
        categoryOfferRepository.save(categoryOffer);
        List<Products> products=productRepository.findByCategory(categoryOffer.getCategory());
        for(Products product:products){
            Float price=product.getPrice()-categoryOffer.getDiscountAmount();
            product.setPrice(price);
            productRepository.save(product);
        }
        return "redirect:/admin/offers";
    }

    @Override
    public void deleteCategoryOffer(Long id) {
        CategoryOffer categoryOffer=categoryOfferRepository.findById(id).orElse(null);
        List<Products> products=productRepository.findByCategory(categoryOffer.getCategory());
        for(Products product:products){

            Float price=product.getPrice()+categoryOffer.getDiscountAmount();
            product.setPrice(price);
            productRepository.save(product);
        }

        categoryOffer.setDiscountAmount(0f);
        categoryOfferRepository.save(categoryOffer);

    }
}
