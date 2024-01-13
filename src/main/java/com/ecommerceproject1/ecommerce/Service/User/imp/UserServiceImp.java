package com.ecommerceproject1.ecommerce.Service.User.imp;

import com.ecommerceproject1.ecommerce.Entity.Admin.Coupon;
import com.ecommerceproject1.ecommerce.Entity.Prodect.Brands;
import com.ecommerceproject1.ecommerce.Entity.Prodect.Categories;
import com.ecommerceproject1.ecommerce.Entity.Prodect.Products;
import com.ecommerceproject1.ecommerce.Entity.user.*;
import com.ecommerceproject1.ecommerce.Exeption.ResourceNotFound;
import com.ecommerceproject1.ecommerce.Repository.*;
import com.ecommerceproject1.ecommerce.Service.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service

public class UserServiceImp implements UserService {

    @Autowired
    private BannerRepository bannerRepository;

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private BrandsRepository brandsRepository;
  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private WishlistRepository wishlistRepository;

  @Autowired
  private UserInfoRepository userInfoRepository;
  @Autowired
  private CartRepository cartRepository;

  @Autowired
  private WalletRepository walletRepository;

  @Autowired
  private CouponRepository couponRepository;




    @Override
    public String shopPage(Model model) {
        List<Categories> categories = categoryRepository.findAll();
        List<Brands> brands = brandsRepository.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("brands", brands);
        model.addAttribute("banner",bannerRepository.findFirstByIsActive(true));
        List<Products> products1 = productRepository.findTop8ByIsActiveTrue();
        model.addAttribute("product",products1);
        UserInfo user=userInfoRepository.findByEmail(currentUserName());
        Cart cart=cartRepository.findByUserUserId(user.getUserId());
        model.addAttribute("cartItemsCount",cart.getCartProducts().size());
        return "user/shop";
    }

//    allproduct(searchKey,model,type,value,page,size,pageable);
@Override
public String allproduct(String searchKey, Model model, String brand, String category, String priceRange, int page, int size, Pageable pageable) {
    // Parse price range (assuming it's in the format "min-max")
    Float minPrice = null;
    Float maxPrice = null;

    if (priceRange != null) {
        String[] prices = priceRange.split("-");
        if (prices.length == 2) {
            minPrice = Float.parseFloat(prices[0]);
            maxPrice = Float.parseFloat(prices[1]);
        }
    }

    // Validate and sanitize input parameters
    if (page < 0) {
        page = 0;
    }
    if (size <= 0) {
        size = 10; // or any default size you prefer
    }

    List<Brands> brandsList = brandsRepository.findAll();
    List<Categories> categoriesList = categoryRepository.findAll();

    model.addAttribute("categories", categoriesList);
    model.addAttribute("brands", brandsList);

    // Check if brand and category are present
    if (brand != null && category != null) {
        // Check if price range is also present
        if (minPrice != null && maxPrice != null) {
            Page<Products> productsPage = productRepository.findByBrandBrandNameAndCategoryCategoryNameAndPriceBetweenAndIsActiveTrueAndIsDeleteFalse(
                    brand, category, minPrice, maxPrice, pageable
            );
            model.addAttribute("products", productsPage);
        } else {
            // Only brand and category are present
            Page<Products> productsPage = productRepository.findByBrandBrandNameAndCategoryCategoryNameAndIsActiveTrueAndIsDeleteFalse(
                    brand, category, pageable
            );
            model.addAttribute("products", productsPage);
        }
    } else if (category != null) {
        // Only category is present
        Page<Products> productsPage = productRepository.findByCategoryCategoryNameAndIsActiveTrueAndIsDeleteFalse(
                category, pageable
        );
        model.addAttribute("products", productsPage);
    } else if (brand != null) {
        // Only brand is present
        Page<Products> productsPage = productRepository.findByBrandBrandNameAndIsActiveTrueAndIsDeleteFalse(
                brand, pageable
        );
        model.addAttribute("products", productsPage);
    } else if (searchKey != null) {
        // Only search key is present
        Page<Products> productsPage = productRepository.findActiveNotDeletedProductsContainingName(searchKey, pageable);
        model.addAttribute("products", productsPage);
    } else {
        // No specific filter, show all products
        Page<Products> productsPage = productRepository.findAllByIsActiveTrueAndIsDeleteFalse(pageable);
        model.addAttribute("products", productsPage);
    }

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

    @Override
    public String getwallet(Model model) {

        UserInfo user = userInfofindByEmail(currentUserName());

        Wallet wallet = user.getWallet();
        if (wallet == null) {
            wallet = new Wallet();
            WalletHistory walletHistory = new WalletHistory();
            walletHistory.setWallet(wallet);

            wallet.setUser(user);
            walletRepository.save(wallet);
        }


        model.addAttribute("wallet",wallet);

        return "user/Wallet";
    }

    @Override
    public String getwishlist(Model model) {
        Wishlist wishlist=wishlistRepository.findByUser_Email(currentUserName());
        model.addAttribute("wishlist",wishlist);
        return "user/wishlist";
    }

    @Override
    public void addProductToWishlist(Long productId) {

        Wishlist wishlist = wishlistRepository.findByUser_Email(currentUserName());
        Optional<Products> productOptional = productRepository.findById(productId);// Retrieve the product by ID (use your product repository here)

        if ( productOptional.isPresent()) {

            Products product = productOptional.get();

            List<WishlistItems> wishitems=wishlist.getWishlistItems();



            WishlistItems existingWishlist = wishitems.stream()
                    .filter(cp -> cp.getProduct().getProductID().equals(productId))
                    .findFirst()
                    .orElse(null);

           if(existingWishlist==null){
               // Create a new WishlistItems instance
               WishlistItems wishlistItem = new WishlistItems();
               wishlistItem.setProduct(product);
               wishlistItem.setWishlist(wishlist);

               // Add the wishlist item to the wishlist
               wishlist.getWishlistItems().add(wishlistItem);

               // Save the updated wishlist
               wishlistRepository.save(wishlist);
           }
        }

    }

    @Override
    public void removeProductFromWishlist(Long productId) {

        Wishlist wishlist = wishlistRepository.findByUser_Email(currentUserName());
        Optional<WishlistItems> wishlistItemOptional = wishlist.getWishlistItems().stream()
                .filter(item -> item.getProduct().getProductID().equals(productId))
                .findFirst();

        wishlistItemOptional.ifPresent(wishlistItem -> {
            wishlist.getWishlistItems().remove(wishlistItem);
            wishlistRepository.save(wishlist);
        });
    }

    @Override
    public String viewCoupon(Model model) {
        LocalDate currentDate = LocalDate.now();

        List<Coupon> coupons=couponRepository.findByIsActiveAndExpirationDateAfter(true,currentDate);
        model.addAttribute("coupons",coupons);
         return "user/view-coupon";
    }

}
