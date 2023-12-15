package com.ecommerceproject1.ecommerce.Service.User;

import com.ecommerceproject1.ecommerce.Entity.user.UserInfo;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public interface UserService {
    String shopPage(Model model);
    String allproduct(Model model);
    String productdetails(Model model,Long id);

    UserInfo userInfofindByEmail(String email);
}
