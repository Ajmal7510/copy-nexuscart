package com.ecommerceproject1.ecommerce.Service.User.imp;

import com.ecommerceproject1.ecommerce.Entity.Prodect.Products;
import com.ecommerceproject1.ecommerce.Entity.user.Cart;
import com.ecommerceproject1.ecommerce.Entity.user.CartProduct;
import com.ecommerceproject1.ecommerce.Entity.user.UserInfo;
import com.ecommerceproject1.ecommerce.Exeption.ResourceNotFound;
import com.ecommerceproject1.ecommerce.Repository.CartRepository;
import com.ecommerceproject1.ecommerce.Repository.ProductRepository;
import com.ecommerceproject1.ecommerce.Repository.UserInfoRepository;
import com.ecommerceproject1.ecommerce.Service.User.CartService;
import com.ecommerceproject1.ecommerce.Service.User.UserInfoService;
import com.ecommerceproject1.ecommerce.Service.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.security.Principal;
import java.util.List;

@Service
public class CartServiceImp implements CartService {
    @Autowired
    private CartRepository cartRepository;
//    @Autowired
//    private UserInfoService userInfoService;

    @Autowired
    private UserInfoRepository userInfoRepository;



    @Autowired
    private UserService userService;
    @Autowired
    private ProductRepository productRepository;

    @Override
    public Cart getcart() {
        Cart cart=new Cart();
        return cartRepository.save(cart);
    }

    @Override
    public String cart(Model model,Principal principal) {
        UserInfo user = userInfoRepository.findByEmail(principal.getName());
        Cart cart = cartRepository.findByUserUserId(user.getUserId());
        List<CartProduct> cartProducts = cart.getCartProducts();
        if (cartProducts != null) {

            model.addAttribute("cardProducts", cartProducts);
            model.addAttribute("cartIsEmpty", false);

        } else {
            model.addAttribute("cartIsEmpty", true);
        }
        int totalItemCount = (cartProducts != null) ? cartProducts.size() : 0;
        model.addAttribute("totalItemCount", totalItemCount);

        model.addAttribute("currentTotal", cart.getTotalCartAmount());

        return "user/cart";
    }

    @Override
    public ResponseEntity<String> addtocar(Long productId) {
        Products product=productRepository.findById(productId).orElse(null);
        UserInfo user=userInfoRepository.findByEmail(userService.currentUserName());
        Cart cart = cartRepository.findByUserUserId(user.getUserId());
        List<CartProduct> cartProducts = cart.getCartProducts();
        if(product==null){
            throw new ResourceNotFound("product Not Fount");
        }
        ;


        CartProduct existingCartProduct = cartProducts.stream()
                .filter(cp -> cp.getProduct().getProductID().equals(productId))
                .findFirst()
                .orElse(null);

//        System.out.println(existingCartProduct.getProduct().getProductName());

        if (existingCartProduct != null) {
            // If the product already exists, increment the quantity
            existingCartProduct.setQuantity(existingCartProduct.getQuantity() + 1);
        } else {
            // If the product doesn't exist, add a new CartProduct
            CartProduct cartProduct = new CartProduct();
            cartProduct.setProduct(product);
            cartProduct.setCart(cart);
            cartProduct.setQuantity(1); // Initial quantity for a new product
            cartProducts.add(cartProduct);
        }

        // Save the updated cart
        cartRepository.save(cart);

        // Recalculate the totalCartAmount
        double totalCartAmount = cartProducts.stream()
                .mapToDouble(cp -> cp.getQuantity() * cp.getProduct().getPrice())
                .sum();

        // Set the totalCartAmount in the userCart
        cart.setTotalCartAmount(totalCartAmount);
        cartRepository.save(cart);

        return new ResponseEntity<>("success", HttpStatus.OK);

    }

    public ResponseEntity<String> removeFromCart(Long productId) {
        UserInfo user = userInfoRepository.findByEmail(userService.currentUserName());
        Cart cart = cartRepository.findByUserUserId(user.getUserId());
        List<CartProduct> cartProducts = cart.getCartProducts();

        // Check if the product exists in the cart
        CartProduct cartProduct = cartProducts.stream()
                .filter(cp -> cp.getProduct().getProductID().equals(productId))
                .findFirst()
                .orElse(null);

        if (cartProduct != null) {
            // Remove the product from the cart
            cartProducts.remove(cartProduct);

            // Recalculate the totalCartAmount
            double totalCartAmount = cartProducts.stream()
                    .mapToDouble(cp -> cp.getQuantity() * cp.getProduct().getPrice())
                    .sum();

            // Set the totalCartAmount in the userCart
            cart.setTotalCartAmount(totalCartAmount);
            cartRepository.save(cart);

            return new ResponseEntity<>("Product removed from the cart", HttpStatus.OK);
        } else {
            throw new ResourceNotFound("Product not found in the cart");
        }
    }
    public ResponseEntity<String> increaseProductQuantity(Long productId) {
        Products product = productRepository.findById(productId).orElse(null);
        UserInfo user = userInfoRepository.findByEmail(userService.currentUserName());
        Cart cart = cartRepository.findByUserUserId(user.getUserId());
        List<CartProduct> cartProducts = cart.getCartProducts();

        if (product == null) {
            throw new ResourceNotFound("Product not found");
        }

        // Check if the product already exists in the cart
        CartProduct cartProduct = cartProducts.stream()
                .filter(cp -> cp.getProduct().getProductID().equals(productId))
                .findFirst()
                .orElse(null);

        if (cartProduct != null) {
            // Check if there is enough stock available
            int currentStock = product.getStock();
            int requestedQuantity = cartProduct.getQuantity() + 1;

            if (requestedQuantity <= currentStock) {
                // Increase the quantity in the cart
                cartProduct.setQuantity(requestedQuantity);

                // Recalculate the totalCartAmount
                double totalCartAmount = cartProducts.stream()
                        .mapToDouble(cp -> cp.getQuantity() * cp.getProduct().getPrice())
                        .sum();

                // Set the totalCartAmount in the userCart
                cart.setTotalCartAmount(totalCartAmount);
                cartRepository.save(cart);

                return new ResponseEntity<>("Product quantity increased in the cart", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Not enough stock available", HttpStatus.BAD_REQUEST);
            }
        } else {
            throw new ResourceNotFound("Product not found in the cart");
        }
    }

    public ResponseEntity<String> decreaseProductQuantity(Long productId) {
        UserInfo user = userInfoRepository.findByEmail(userService.currentUserName());
        Cart cart = cartRepository.findByUserUserId(user.getUserId());
        List<CartProduct> cartProducts = cart.getCartProducts();

        // Check if the product already exists in the cart
        CartProduct cartProduct = cartProducts.stream()
                .filter(cp -> cp.getProduct().getProductID().equals(productId))
                .findFirst()
                .orElse(null);

        if (cartProduct != null) {
            // Decrease the quantity in the cart
            int currentQuantity = cartProduct.getQuantity();

            if (currentQuantity > 1) {
                cartProduct.setQuantity(currentQuantity - 1);

                // Recalculate the totalCartAmount
                double totalCartAmount = cartProducts.stream()
                        .mapToDouble(cp -> cp.getQuantity() * cp.getProduct().getPrice())
                        .sum();

                // Set the totalCartAmount in the userCart
                cart.setTotalCartAmount(totalCartAmount);
                cartRepository.save(cart);

                return new ResponseEntity<>("Product quantity decreased in the cart", HttpStatus.OK);
            } else {
                // If the quantity is 1, remove the product from the cart
                cartProducts.remove(cartProduct);

                // Recalculate the totalCartAmount
                double totalCartAmount = cartProducts.stream()
                        .mapToDouble(cp -> cp.getQuantity() * cp.getProduct().getPrice())
                        .sum();

                // Set the totalCartAmount in the userCart
                cart.setTotalCartAmount(totalCartAmount);
                cartRepository.save(cart);

                return new ResponseEntity<>("Product removed from the cart", HttpStatus.OK);
            }
        } else {
            throw new ResourceNotFound("Product not found in the cart");
        }
    }




}
