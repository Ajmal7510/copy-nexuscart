package com.ecommerceproject1.ecommerce.Repository;

import com.ecommerceproject1.ecommerce.Entity.Prodect.Brands;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.nio.file.LinkOption;
@Repository
public interface BrandsRepository extends JpaRepository<Brands,Long> {

    boolean existsByBrandName(String brandName);

}
