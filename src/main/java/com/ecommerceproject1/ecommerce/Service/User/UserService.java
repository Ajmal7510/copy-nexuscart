package com.ecommerceproject1.ecommerce.Service.User;

import com.ecommerceproject1.ecommerce.Entity.user.UserInfo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public interface UserService {
    String shopPage(Model model);
    String allproduct(String searchKey, Model model, String type, String value,int page,int size);
    String productdetails(Model model,Long id);

    UserInfo userInfofindByEmail(String email);
    public String currentUserName();
    String getwallet(Model model);

    String getwishlist(Model model);

   void addProductToWishlist(Long productId);
   void removeProductFromWishlist(Long productId);

}
