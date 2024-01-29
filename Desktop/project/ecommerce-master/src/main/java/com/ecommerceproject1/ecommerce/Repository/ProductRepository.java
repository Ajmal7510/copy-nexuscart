package com.ecommerceproject1.ecommerce.Repository;

import com.ecommerceproject1.ecommerce.Entity.Prodect.Categories;
import com.ecommerceproject1.ecommerce.Entity.Prodect.Products;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository  extends JpaRepository<Products,Long> {
    List<Products> findByIsActiveTrue();
    List<Products> findTop8ByIsActiveTrue();
    Optional<Products> findByProductIDAndIsActiveTrueAndIsDeleteFalse(Long productId);

    List<Products> findByCategory(Categories categories);

    List<Products> findAllByIsDeleteFalse();
    List<Products> findByIsActiveTrueAndIsDeleteFalseAndProductNameContaining(String productName);
    List<Products> findAllByIsActiveTrueAndIsDeleteFalse();

    @Query("SELECT p FROM Products p " +
            "WHERE p.isActive = true " +
            "AND p.isDelete = false " +
            "AND LOWER(p.productName) LIKE LOWER(CONCAT('%', :productName, '%'))")
    Page<Products> findActiveNotDeletedProductsContainingName(@Param("productName") String productName,Pageable pageable);

//    Page<Products> findActiveNotDeletedProductsContainingName(@Param("productName") String productName, Pageable pageable);
//       Page<Products> findAllByIsActiveTrueAndIsDeleteFalse(Pageable pageable);

    Page<Products> findAllByIsActiveTrueAndIsDeleteFalse(Pageable pageable);

    Page<Products> findByBrandBrandNameAndCategoryCategoryNameAndPriceBetweenAndIsActiveTrueAndIsDeleteFalse(
            String brandName, String categoryName, Float minPrice, Float maxPrice, Pageable pageable
    );

    Page<Products> findByBrandBrandNameAndCategoryCategoryNameAndIsActiveTrueAndIsDeleteFalse(
            String brandName, String categoryName, Pageable pageable
    );
    Page<Products> findByCategoryCategoryNameAndIsActiveTrueAndIsDeleteFalse(
            String categoryName, Pageable pageable
    );
    Page<Products> findByBrandBrandNameAndIsActiveTrueAndIsDeleteFalse(
            String brandName, Pageable pageable
    );





}