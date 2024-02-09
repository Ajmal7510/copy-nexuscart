package com.ecommerceproject1.ecommerce.Service.Prodect;

import com.ecommerceproject1.ecommerce.Entity.Prodect.Brands;
import com.ecommerceproject1.ecommerce.Entity.Prodect.Categories;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    boolean CategoryExist(String category);
    void SaveCategory(Categories category);

    List<Categories>  findAllCategories();

     void deleteCategory(Long id);



     // brandservice

    List<Brands> finallbyBrand();

    boolean brandExists(String brandName);

    void SaveBrand(Brands brands);

    void deleteBrand(Long brandId);
    Categories getCategoryById(Long categoryId);

    Brands getBrandById(Long brandId);
    Categories findById(Long id);
    Brands findByBrandId(Long id);
}
