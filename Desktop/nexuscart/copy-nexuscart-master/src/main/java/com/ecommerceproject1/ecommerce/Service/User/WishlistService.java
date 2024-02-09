package com.ecommerceproject1.ecommerce.Service.User;

import com.ecommerceproject1.ecommerce.Entity.user.Wishlist;
import org.springframework.stereotype.Service;

@Service
public interface WishlistService {
    Wishlist getWishlist();
}
