package com.ecommerceproject1.ecommerce.Repository;

import com.ecommerceproject1.ecommerce.Entity.Prodect.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository  extends JpaRepository<Products,Long> {
    List<Products> findByIsActiveTrue();
    List<Products> findTop8ByIsActiveTrue();
    Optional<Products> findByProductIDAndIsActiveTrueAndIsDeleteFalse(Long productId);

    List<Products> findAllByIsDeleteFalse();

//    Page<Products> findByIsActiveTrue(Pageable pageable);
}