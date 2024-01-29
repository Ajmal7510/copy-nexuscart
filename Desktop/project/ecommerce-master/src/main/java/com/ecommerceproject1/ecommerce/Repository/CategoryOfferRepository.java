package com.ecommerceproject1.ecommerce.Repository;

import com.ecommerceproject1.ecommerce.Entity.Offers.CategoryOffer;
import com.ecommerceproject1.ecommerce.Entity.Prodect.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CategoryOfferRepository extends JpaRepository<CategoryOffer,Long> {
    List<CategoryOffer> findByExpirationDateBefore(LocalDate currentDate);
    CategoryOffer findByCategory(Categories category);
}
