package com.ecommerceproject1.ecommerce.Service.User.imp;

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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.awt.print.Pageable;
import java.util.ArrayList;
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

    @Override
    public String allproduct(String searchKey, Model model, String type, String value,int page,int size) {

        // Validate and sanitize input parameters
        if (page < 0) {
            page = 0;
        }
        if (size <= 0) {
            size = 10; // or any default size you prefer
        }


        List<Categories> categories = categoryRepository.findAll();

        model.addAttribute("categories", categories);


        Page<Products> productsPage = null;

        if (searchKey == null && type == null && value == null) {
//            List<Products> products = productRepository.findAllByIsActiveTrueAndIsDeleteFalse();

            productsPage = productRepository.findAllByIsActiveTrueAndIsDeleteFalse((Pageable) PageRequest.of(page, size));
//            System.out.println(products);

//            model.addAttribute("products", products);
        } else if (type != null && value != null) {

//            List<Products> filetProducts = filterByTypeValue(type, value);
//
//            model.addAttribute("products", filetProducts);

        } else {
            List<Products> productsList = productRepository.findActiveNotDeletedProductsContainingName(searchKey);
//            productsPage = productRepository.findActiveNotDeletedProductsContainingName(searchKey, PageRequest.of(page, size));

//            if (!(productsList.isEmpty())) {
//                model.addAttribute("products", productsList);
//            }
        }


        model.addAttribute("products", productsPage.getContent());

        // Add pagination information to the model
        model.addAttribute("currentPage", productsPage.getNumber());
        model.addAttribute("totalPages", productsPage.getTotalPages());
        model.addAttribute("totalItems", productsPage.getTotalElements());
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

}
