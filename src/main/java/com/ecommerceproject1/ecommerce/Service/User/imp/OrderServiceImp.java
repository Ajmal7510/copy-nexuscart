package com.ecommerceproject1.ecommerce.Service.User.imp;

import com.ecommerceproject1.ecommerce.Entity.Admin.Coupon;
import com.ecommerceproject1.ecommerce.Entity.user.*;
import com.ecommerceproject1.ecommerce.Repository.*;
import com.ecommerceproject1.ecommerce.Service.Admin.CouponService;
import com.ecommerceproject1.ecommerce.Service.Prodect.ProductService;
import com.ecommerceproject1.ecommerce.Service.User.CartService;
import com.ecommerceproject1.ecommerce.Service.User.OrderProductService;
import com.ecommerceproject1.ecommerce.Service.User.OrderService;
import com.ecommerceproject1.ecommerce.Service.User.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class OrderServiceImp implements OrderService {

    @Autowired
    CouponService couponService;

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderProductService orderProductService;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductService productService;


    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private CouponRepository couponRepository;





    private String getCurrentIndianTime() {
        ZoneId indianZone = ZoneId.of("Asia/Kolkata");
        LocalDateTime currentTime = LocalDateTime.now(indianZone);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return currentTime.format(formatter);
    }
    private LocalDate getCurrentIndianDate() {
        ZoneId indianZone = ZoneId.of("Asia/Kolkata");
        return LocalDate.now(indianZone);
    }

    @Override
    public String checkOut(String coupon,Model model) {
        String email = userService.currentUserName();
        Wallet wallet=walletRepository.findByUser_Email(email);
        UserInfo user = userService.userInfofindByEmail(email);
        Cart cart = cartRepository.findByUserUserId(user.getUserId());
        List<CartProduct> cartProducts = cart.getCartProducts();


        double totalAmount = cart.getTotalCartAmount();
        double discountAmount=0.0;

        if (coupon != null && !coupon.isEmpty()) {
            // Assuming you have a CouponService with a method to validate and apply discounts
            Coupon coupons = couponService.validateCoupon(coupon);

            if (coupons != null) {
                if(coupons.getMinimumAmount()>cart.getTotalCartAmount()){
                    System.out.println("it working ");

                    model.addAttribute("amountError","this Conpen minmumAmount"+coupons.getMinimumAmount());

                    model.addAttribute("showinput", " ");
                }else {
                    System.out.println("its working");
                    // Calculate discount based on percentage
                    double discountPercentage = coupons.getDiscountPercentage() / 100.0;
                    discountAmount = totalAmount * discountPercentage;
                    System.out.println(coupons.getDiscountPercentage());
                    System.out.println(discountPercentage);
                    System.out.println(discountAmount);

                    // You can apply more complex logic if needed
                    // Update the model with information about the applied coupon if needed
                    model.addAttribute("successCoupon","success fully applyed Copon Code:"+coupons.getCouponCode());
                    model.addAttribute("appliedCoupon", coupon);
                }

            }else {
                model.addAttribute("invalid","invalid coupon code");
                model.addAttribute("showinput", " ");
            }
        }else {
            model.addAttribute("showinput", " ");

        }

        model.addAttribute("discountAmount", discountAmount);


        totalAmount = cart.getTotalCartAmount()-discountAmount;


        model.addAttribute("wallet",wallet);
        model.addAttribute("cartProducts", cartProducts);
        model.addAttribute("addresses", user.getUserAddresses());
        model.addAttribute("totalAmount", totalAmount);
        model.addAttribute("items",cart.getCartProducts());

        return "user/checkout-page";
    }

    @Override
    public ResponseEntity<Boolean> checkOutValidation(Model model) {
        Cart cart = cartRepository.findByUserEmail(userService.currentUserName());
        boolean isAnyProductInactive = cart.getCartProducts().stream()
                .anyMatch(cartProduct ->  !cartProduct.getProduct().isActive() || cartProduct.getProduct().getStock() < 2);
        return ResponseEntity.ok(isAnyProductInactive);
    }

//    @Override
//    public String orderitem(Long addressId, String payment) {
//        UserInfo user=userService.userInfofindByEmail(userService.currentUserName());
//        Cart cart=user.getCart();
//        Orders orders=new Orders();
//        orderRepository.save(orders);
//
//        List<CartProduct> cartProducts=cart.getCartProducts();
//
//
//
//        List<OrderItem> orderItems =new ArrayList<>();
//        for(CartProduct cartProduct:cartProducts){
//           orderItems.add(orderProductService.save(cartProduct,orders));
//        }
//
//
//        double subTotalAmount=cart.getTotalCartAmount();
//        double totalAmount=subTotalAmount;
//
//        orders.setUser(user);
//        orders.setAddress(addressRepository.findById(addressId).orElse(null));
//        orders.setOrderProducts(orderItems);
//        orders.setOrderDate(getCurrentIndianDate());
//        orders.setTotalAmount(subTotalAmount);
//
//
//        if (!payment.equals("cashOnDelivery")) {
//            // If payment method is NOT "cashOnDelivery"
//            orders.setAmountStatus("Pending");
//        } else {
//            // If payment method is "cashOnDelivery"
//            orders.setAmountStatus("pending");
//        }
//        Payments payments=payment(payment,user,orders,totalAmount);
//
//        orders.setPayments(payments);
//        orderRepository.save(orders);
//        paymentRepository.save(payments);
//
//
////        for(OrderItem item:orderItems){
////            productService.reduceStock(item.getProduct().getProductID(),item.getQuantity());
////        }
//        for(CartProduct cartProduct:cartProducts){
//            productService.reduceStock(cartProduct.getProduct().getProductID(),cartProduct.getQuantity());
//        }
//
//        cart.setTotalCartAmount(0.0);
//        cart.getCartProducts().clear();
//        cartRepository.save(cart);
//
//
//
//
//        return "user/oderpage";
//    }

    @Override
    public String orderitem(Long addressId, String payment,String coupon) {
        UserInfo user = userService.userInfofindByEmail(userService.currentUserName());
        Cart cart = user.getCart();

        // Create and save the Orders entity
        Orders orders = new Orders();
        orders.setUser(user);
        orders.setAddress(addressRepository.findById(addressId).orElse(null));
        orders.setOrderDate(getCurrentIndianDate());
        orderRepository.save(orders);

        List<CartProduct> cartProducts = cart.getCartProducts();
        List<OrderItem> orderItems = new ArrayList<>();

        for (CartProduct cartProduct : cartProducts) {
            // Create and save each OrderItem entity
            OrderItem orderItem = orderProductService.save(cartProduct, orders);
            orderItems.add(orderItem);
        }

        double discountAmount=0;
        if (coupon != null && !coupon.isEmpty()) {
            // Assuming you have a CouponService with a method to validate and apply discounts
            Coupon coupons = couponService.validateCoupon(coupon);

            if (coupons != null) {
                if(coupons.getMinimumAmount()<cart.getTotalCartAmount()){

                }else {
                    // Calculate discount based on percentage
                    double discountPercentage = coupons.getDiscountPercentage() / 100.0;
                    discountAmount = cart.getTotalCartAmount() * discountPercentage;
                    System.out.println(coupons.getDiscountPercentage());
                    System.out.println(discountPercentage);



                }

            }
        }

        double subTotalAmount = cart.getTotalCartAmount();


        double totalAmount = subTotalAmount-discountAmount;

        // Set additional properties for the Orders entity
        orders.setOrderProducts(orderItems);
        orders.setTotalAmount(totalAmount);

        if (payment.equals("cod")) {
            orders.setAmountStatus("Pending");
        }else if(payment.equals("wallet")){
            Wallet wallet=walletRepository.findByUser_Email(user.getEmail());
            wallet.setWalletTotalAmount(wallet.getWalletTotalAmount()-totalAmount);
         List<WalletHistory>  walletHistories = wallet.getWalletHistory();

        WalletHistory walletHistory = new WalletHistory();
        walletHistory.setWithdrawAmount(totalAmount);
        walletHistory.setDepositOrWithdraw("Withdraw");
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        walletHistory.setAmountWithdrawTime(currentDateTime.format(formatter));
        walletHistory.setWallet(wallet);

        walletHistories.add(walletHistory);
            walletRepository.save(wallet);
            orders.setAmountStatus("paid");

        } else {
            orders.setAmountStatus("paid");
        }

        // Save the updated Orders entity
        orderRepository.save(orders);

        // Create and save Payments entity
        Payments payments = payment(payment, user, orders, totalAmount);
        payments.setOrders(orders);
        paymentRepository.save(payments);

        // Update product stock and save changes
        for (CartProduct cartProduct : cartProducts) {
            productService.reduceStock(cartProduct.getProduct().getProductID(), cartProduct.getQuantity());
        }

        // Clear cart and save changes
        cart.setTotalCartAmount(0.0);
        cart.getCartProducts().clear();
        cartRepository.save(cart);

        return "user/oderpage";
    }




    @Override
    public String myOrders(Model model) {
        String email = userService.currentUserName();
        UserInfo user = userService.userInfofindByEmail(email);

        List<OrderItem> allProducts = new ArrayList<>();

        List<Orders> orders = orderRepository.findByUserOrderByOrderIdDesc(user);
        for (Orders order : orders) {
            allProducts.addAll(order.getOrderProducts());
        }


            model.addAttribute("products", allProducts);
            return "user/orders";


    }

    @Override
    public String orderDetails(Long productId, Model model) {
        try {
            Orders orders = orderRepository.findById(productId).orElse(null);
            double orderStatusPercentage = 0.0;


            if ("Ordered".equals(orders.getStatus())) {
                orderStatusPercentage = 0.0;
            }else if("Order Placed".equals(orders.getStatus())){
                orderStatusPercentage=25.0;

            }else if ("Shipped".equals(orders.getStatus())) {
                orderStatusPercentage = 50.0;
            } else if ("Delivered".equals(orders.getStatus())) {
                orderStatusPercentage = 100.0;
            }

            model.addAttribute("orderStatusPercentage", orderStatusPercentage);

            model.addAttribute("orders", orders);

            return "user/orderDetails";
        } catch (Exception e) {

            return "Exception/404";
        }
    }

    @Override
    public String cancelOrder(Long orderId) {
        try {
            UserInfo user = userService.userInfofindByEmail(userService.currentUserName());
            Orders orders = orderRepository.findById(orderId).get();
            Wallet wallet = user.getWallet();

            if (orders.getPayments().getPaymentMethod().equals("cod") && !(orders.getStatus().equals("Delivered") || orders.getStatus().equals("Return"))) {
                orders.setCancelled(true);
                orders.setStatus("Cancelled");
            } else if (!(orders.getPayments().getPaymentMethod().equals("cod")) && !(orders.getStatus().equals("Delivered") || orders.getStatus().equals("Return"))) {
                List<WalletHistory> walletHistories;

                if (wallet == null) {
                    wallet = new Wallet();
                    walletHistories = new ArrayList<>();
                } else {
                    walletHistories = wallet.getWalletHistory();
                }
                WalletHistory walletHistory = new WalletHistory();
                walletHistory.setAddedAmount(orders.getTotalAmount());
                walletHistory.setDepositOrWithdraw("Deposit");
                LocalDateTime currentDateTime = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                walletHistory.setAmountAddedTime(currentDateTime.format(formatter));
                walletHistory.setWallet(wallet);

                walletHistories.add(walletHistory);

                double totalAmount = walletHistories.stream().mapToDouble(WalletHistory::getAddedAmount).sum();

                log.info("User TotalWalletAmount" + totalAmount); // Log the user's total wallet amount

                wallet.setWalletTotalAmount(totalAmount);
                orders.setStatus("Cancelled");

                wallet.setWalletHistory(walletHistories);
                wallet.setUser(user);
            }

            orderRepository.save(orders);
            walletRepository.save(wallet);
        } catch (Exception e) {
            log.info("cancelOrder" + e.getMessage());
            return "redirect:/error-page";
        }

        return "redirect:/user/orders";
    }

    @Override
    public String returnOrder(Long orderId) {
        try {
            UserInfo user = userService.userInfofindByEmail(userService.currentUserName());
            Orders orders = orderRepository.findById(orderId).get();
            Wallet wallet = user.getWallet();

            if (orders.getPayments().getPaymentMethod().equals("cod") && !(orders.getStatus().equals("Delivered") || orders.getStatus().equals("Return"))) {

                orders.setStatus("Return Processing");
            }
                List<WalletHistory> walletHistories;

                if (wallet == null) {
                    wallet = new Wallet();
                    walletHistories = new ArrayList<>();
                } else {
                    walletHistories = wallet.getWalletHistory();
                }
            orders.setStatus("Return Processing");
                WalletHistory walletHistory = new WalletHistory();
                walletHistory.setAddedAmount(orders.getTotalAmount());
                walletHistory.setDepositOrWithdraw("Deposit");
                LocalDateTime currentDateTime = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                walletHistory.setAmountAddedTime(currentDateTime.format(formatter));
                walletHistory.setWallet(wallet);

                walletHistories.add(walletHistory);

                double totalAmount = walletHistories.stream().mapToDouble(WalletHistory::getAddedAmount).sum();

                log.info("User TotalWalletAmount" + totalAmount); // Log the user's total wallet amount

                wallet.setWalletTotalAmount(totalAmount);

                wallet.setWalletHistory(walletHistories);

                wallet.setUser(user);


            orderRepository.save(orders);
            walletRepository.save(wallet);
        } catch (Exception e) {
            log.info("cancelOrder" + e.getMessage());
            return "redirect:/error-page";
        }

        return "redirect:/user/orders";
    }


    Payments payment(String paymentMethod,UserInfo user,Orders orders,double amount){
        Payments payment = new Payments();
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        payment.setPaymentMethod(paymentMethod);
        payment.setAmount(amount);
        payment.setOrders(orders);


            if(paymentMethod.equals("online")) {
                payment.setStatus("Paid");
                orders.setAmountStatus("Paid");
                payment.setPaymentTime(currentDateTime.format(formatter));
            }

        if(paymentMethod.equals("wallet")) {
            payment.setStatus("Paid");
            orders.setAmountStatus("Paid");
            payment.setPaymentTime(currentDateTime.format(formatter));
        }
        log.info("Payment Method: " + paymentMethod);
        log.info("Payment Status: " + payment.getStatus());
        log.info("Orders Amount Status: " + orders.getAmountStatus());


        return payment;
    }


}
