package com.ecommerceproject1.ecommerce.Repository;

import com.ecommerceproject1.ecommerce.Entity.Prodect.ProductOffers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductOfferRepository extends JpaRepository<ProductOffers,Long> {

}
