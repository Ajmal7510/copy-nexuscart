package com.ecommerceproject1.ecommerce.Service.User.imp;

import com.ecommerceproject1.ecommerce.Entity.user.CartProduct;
import com.ecommerceproject1.ecommerce.Entity.user.OrderItem;
import com.ecommerceproject1.ecommerce.Entity.user.Orders;
import com.ecommerceproject1.ecommerce.Repository.OrderItemRepository;
import com.ecommerceproject1.ecommerce.Service.User.OrderProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderProductServiceImp implements OrderProductService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public OrderItem save(CartProduct cartProduct,Orders orders) {
        OrderItem orderItem = new OrderItem(cartProduct);
        orderItem.setOrders(orders);
        return orderItemRepository.save(orderItem);
    }
}
