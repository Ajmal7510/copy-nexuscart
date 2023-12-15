package com.ecommerceproject1.ecommerce.Service.Prodect.impl;

import com.ecommerceproject1.ecommerce.Entity.Prodect.Brands;
import com.ecommerceproject1.ecommerce.Entity.Prodect.Categories;
import com.ecommerceproject1.ecommerce.Repository.BrandsRepository;
import com.ecommerceproject1.ecommerce.Repository.CategoryRepository;
import com.ecommerceproject1.ecommerce.Service.Prodect.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceimp implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BrandsRepository brandsRepository;


    @Override
    public boolean CategoryExist(String category) {
        return categoryRepository.existsByCategoryName(category);
    }

    @Override
    public void SaveCategory(Categories c) {
        categoryRepository.save(c);

    }

    @Override
    public List<Categories> findAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public void deleteCategory(Long brandId) {
        categoryRepository.deleteById(brandId);
    }
    //brand service
    @Override
    public List<Brands> finallbyBrand() {
        return brandsRepository.findAll();
    }

    @Override
    public boolean brandExists(String brandName) {
        return brandsRepository.existsByBrandName(brandName);
    }

    @Override
    public void SaveBrand(Brands brands) {
        brandsRepository.save(brands);
    }

    @Override
    public void deleteBrand(Long brandId) {
        brandsRepository.deleteById(brandId);
    }

    @Override
    public Categories getCategoryById(Long categoryId) {
        Optional<Categories> optionalCategory = categoryRepository.findById(categoryId);

        return optionalCategory.orElse(null);
    }

    @Override
    public Brands getBrandById(Long brandId) {
        Optional<Brands> optionalBrands=brandsRepository.findById(brandId);
        return optionalBrands.orElse(null);
    }

    @Override
    public Categories findById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public Brands findByBrandId(Long id) {
        return brandsRepository.findById(id).orElse(null);
    }


}
