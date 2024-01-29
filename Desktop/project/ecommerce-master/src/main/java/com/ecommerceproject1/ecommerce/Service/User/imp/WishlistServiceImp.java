package com.ecommerceproject1.ecommerce.Service.User.imp;

import com.ecommerceproject1.ecommerce.Entity.user.Wishlist;
import com.ecommerceproject1.ecommerce.Repository.WishlistRepository;
import com.ecommerceproject1.ecommerce.Service.User.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WishlistServiceImp implements WishlistService {
    @Autowired
    WishlistRepository wishlistRepository;

    @Override
    public Wishlist getWishlist() {
        Wishlist wishlist=new Wishlist();
        return wishlistRepository.save(wishlist);
    }
}
