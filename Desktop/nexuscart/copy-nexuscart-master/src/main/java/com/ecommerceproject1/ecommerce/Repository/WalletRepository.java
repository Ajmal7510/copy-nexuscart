package com.ecommerceproject1.ecommerce.Repository;

import com.ecommerceproject1.ecommerce.Entity.user.UserInfo;
import com.ecommerceproject1.ecommerce.Entity.user.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet,Long> {
    Wallet findByUser(UserInfo user);
    Wallet findByUser_Email(String email);
}
