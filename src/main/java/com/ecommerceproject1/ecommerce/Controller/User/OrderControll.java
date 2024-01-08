package com.ecommerceproject1.ecommerce.Controller.User;

import com.ecommerceproject1.ecommerce.Service.User.OrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
public class OrderControll {

    @Autowired
    private OrderService orderService;

    @GetMapping("/checkout")
    public String checkOut( @RequestParam(name = "couponCode", required = false) String couponCode,Model model) {
        return orderService.checkOut(couponCode,model);
    }


    @GetMapping("/checkout/validation")
    public ResponseEntity<Boolean> checkOutValidation(Model model) {
        return orderService.checkOutValidation(model);
    }

    @PostMapping("/checkout/order-item")
    public String orderItem(@RequestParam(name = "address") long addressId,
                            @RequestParam(name = "payment") String payment,
                            @RequestParam(name = "coupon",required = false) String coupon)  {
        return orderService.orderitem(addressId,payment,coupon);
    }

    @GetMapping("/orders")
    public String myOders(Model model){
        return orderService.myOrders(model);
    }
    @GetMapping("/orderDetails")
    public String orderDetails(@RequestParam("orderId") Long orderId, Model model) {
        return orderService.orderDetails(orderId, model);
    }

    @GetMapping("/cancelOrder")
    public String cancelOrder(@RequestParam("orderId") Long orderId) {
        return orderService.cancelOrder(orderId);
    }

    @GetMapping("/returnOrder")
    public String returnOreder(@RequestParam("orderId")Long orderId){
        return orderService.returnOrder(orderId);
    }
}
