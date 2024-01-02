package com.ecommerceproject1.ecommerce.Service.Admin;

import com.ecommerceproject1.ecommerce.Entity.user.OrderItem;
import com.ecommerceproject1.ecommerce.Entity.user.Orders;
import com.ecommerceproject1.ecommerce.Entity.user.UserInfo;
import com.ecommerceproject1.ecommerce.Repository.OrderRepository;
import com.ecommerceproject1.ecommerce.Service.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminOrderSeviceimp implements AdminOrderService{

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserService userService;
    @Override
    public String Orders(Model model) {
        try {
            List<Orders> orders = orderRepository.findByOrderByOrderDateDesc();
//            List<Orders> orders = orderRepository.findAll();


            model.addAttribute("orders",orders);




        } catch (Exception e) {

            return "Exception/404";
        }
        return "admin/orderlist";
    }

    @Override
    public ResponseEntity<String> updateOrderStatus(Long orderId, String status) {
        try {
            System.out.println(orderId+status);

           Orders order= orderRepository.findById(orderId).get();
            if (status.equals("Delivered")) {
                order.setDeliveredDate(dateFinder(0));
                order.setReturnExpiryDate(dateFinder(7));
                order.getPayments().setStatus("Paid");
                order.getPayments().setPaymentTime(dateFinder(0).toString());
            } else if (status.equals("Cancelled")) {
                order.setCancelled(true);
            }

            order.setStatus(status);
            orderRepository.save(order);
            // Assuming success for this example
            Map<String, String> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "Order status updated successfully.");
            return  ResponseEntity.ok(response.toString());
        } catch (Exception e) {
            // Log the exception or handle it accordingly
            Map<String, String> response = new HashMap<>();
            response.put("status", "error");
            response.put("message", "Failed to update order status.");
            return ResponseEntity.status(500).body(response.toString());
        }
    }

    @Override
    public String orderDeatails(Long orderId, Model model) {
       try {
           Orders orders=orderRepository.findById(orderId).get();
           model.addAttribute("order",orders);

           return "admin/OrderDetails";
       }catch (Exception e){
           return "Exeption/404";
       }
    }


    public LocalDate dateFinder(int numOfDates) {
        ZonedDateTime kolkataTime = ZonedDateTime.now(ZoneId.of("Asia/Kolkata"));

        ZonedDateTime date = kolkataTime.plusDays(numOfDates);

        return date.toLocalDate();
    }
}
