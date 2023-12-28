package com.ecommerceproject1.ecommerce.Service.User.imp;

import com.ecommerceproject1.ecommerce.Entity.Prodect.Brands;
import com.ecommerceproject1.ecommerce.Entity.Prodect.Categories;
import com.ecommerceproject1.ecommerce.Entity.Prodect.Products;
import com.ecommerceproject1.ecommerce.Entity.user.Cart;
import com.ecommerceproject1.ecommerce.Entity.user.UserInfo;
import com.ecommerceproject1.ecommerce.Exeption.ResourceNotFound;
import com.ecommerceproject1.ecommerce.Repository.*;
import com.ecommerceproject1.ecommerce.Service.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
@Service
public class UserServiceImp implements UserService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private BrandsRepository brandsRepository;
  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private UserInfoRepository userInfoRepository;
  @Autowired
  private CartRepository cartRepository;


    @Override
    public String shopPage(Model model) {
        List<Categories> categories = categoryRepository.findAll();
        List<Brands> brands = brandsRepository.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("brands", brands);
        List<Products> products1 = productRepository.findTop8ByIsActiveTrue();
        model.addAttribute("product",products1);
        UserInfo user=userInfoRepository.findByEmail(currentUserName());
        Cart cart=cartRepository.findByUserUserId(user.getUserId());
        model.addAttribute("cartItemsCount",cart.getCartProducts().size());
        return "user/shop";
    }

    @Override
    public String allproduct(Model model) {
        List<Products>  products=productRepository.findByIsActiveTrue();
        model.addAttribute("product",products);
        UserInfo user=userInfoRepository.findByEmail(currentUserName());
        Cart cart=cartRepository.findByUserUserId(user.getUserId());
        model.addAttribute("cartItemsCount",cart.getCartProducts().size());
        return "user/listallproduct";
    }



    @Override
    public String productdetails(Model model,Long id) {
        Products  products=productRepository.findByProductIDAndIsActiveTrueAndIsDeleteFalse(id).orElse(null);
        if (products == null) {
            throw new ResourceNotFound("Product not found");
        }
        model.addAttribute("product",products);
        UserInfo user=userInfoRepository.findByEmail(currentUserName());
        Cart cart=cartRepository.findByUserUserId(user.getUserId());
        model.addAttribute("cartItemsCount",cart.getCartProducts().size());
        return "user/Productdetails";
    }

    @Override
    public UserInfo userInfofindByEmail(String email) {
        return userInfoRepository.findByEmail(email);
    }

    @Override
    public String currentUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

}
