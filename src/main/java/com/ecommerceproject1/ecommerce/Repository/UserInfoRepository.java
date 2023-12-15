package com.ecommerceproject1.ecommerce.Repository;

import com.ecommerceproject1.ecommerce.Entity.user.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo,Long> {

// Optional<UserInfo> findByEmail(String email);
 UserInfo findByEmail(String email);

 List<UserInfo> findByRole(String role);
 List<UserInfo> findByRoleOrderByNameAsc(String role);

}
