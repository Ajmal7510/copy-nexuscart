package com.ecommerceproject1.ecommerce.Repository;

import com.ecommerceproject1.ecommerce.Entity.Admin.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BannerRepository extends JpaRepository<Banner,Long> {

    Banner findFirstByIsActive(boolean isActive);
    Banner findFirstByIsActiveOrderByBannerIdAsc(boolean isActive);

    List<Banner> findAllByOrderByBannerIdAsc();

}
