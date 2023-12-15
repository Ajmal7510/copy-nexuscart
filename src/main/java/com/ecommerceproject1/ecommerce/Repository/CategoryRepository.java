package com.ecommerceproject1.ecommerce.Repository;
import com.ecommerceproject1.ecommerce.Entity.Prodect.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Categories, Long> {

    boolean existsByCategoryName(String name);
    Optional<Categories> findById(Long categoryId);

}
