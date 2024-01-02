package com.ecommerceproject1.ecommerce.Service.User.imp;

import com.ecommerceproject1.ecommerce.Entity.user.*;
import com.ecommerceproject1.ecommerce.Repository.*;
import com.ecommerceproject1.ecommerce.Service.Prodect.ProductService;
import com.ecommerceproject1.ecommerce.Service.User.CartService;
import com.ecommerceproject1.ecommerce.Service.User.OrderProductService;
import com.ecommerceproject1.ecommerce.Service.User.OrderService;
import com.ecommerceproject1.ecommerce.Service.User.UserService;
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
public class OrderServiceImp implements OrderService {

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
    public String checkOut(Model model) {
        String email = userService.currentUserName();
        UserInfo user = userService.userInfofindByEmail(email);
        Cart cart = cartRepository.findByUserUserId(user.getUserId());
        List<CartProduct> cartProducts = cart.getCartProducts();

        double totalAmount = 0.0;


        totalAmount = cart.getTotalCartAmount();

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
    public String orderitem(Long addressId, String payment) {
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

        double subTotalAmount = cart.getTotalCartAmount();
        double totalAmount = subTotalAmount;

        // Set additional properties for the Orders entity
        orders.setOrderProducts(orderItems);
        orders.setTotalAmount(subTotalAmount);

        if (!payment.equals("cashOnDelivery")) {
            orders.setAmountStatus("Pending");
        } else {
            orders.setAmountStatus("pending");
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

        List<Orders> orders = orderRepository.findByUser(user);
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


    Payments payment(String paymentMethod,UserInfo user,Orders orders,double amount){
        Payments payment = new Payments();
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        payment.setPaymentMethod(paymentMethod);
        payment.setAmount(amount);
        payment.setOrders(orders);
        return payment;

    }

//    public List<OrderItem> productMapping(String email, Orders orders) {
//
////        productMapping from CartProduct To OrderProducts;
//        Cart cart = cartRepository.findByUserEmail(email);
//
//        List<OrderItem> orderProductsList = cart.getCartProducts().stream().map(cartProduct -> {
//            OrderItem orderProducts = new OrderItem();
//            orderProducts.setProduct(cartProduct.getProduct());
//            orderProducts.setQuantity(cartProduct.getQuantity());
//            orderProducts.setOrders(orders);
//            return orderProducts;
//        }).toList();
//
//
//        return orderProductsList;
//
//    }
}
