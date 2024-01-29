package com.ecommerceproject1.ecommerce.Service.User;

import com.ecommerceproject1.ecommerce.Entity.user.CartProduct;
import com.ecommerceproject1.ecommerce.Entity.user.OrderItem;
import com.ecommerceproject1.ecommerce.Entity.user.Orders;
import org.springframework.stereotype.Service;

@Service
public interface OrderProductService {
    public OrderItem save(CartProduct cartProduct,Orders orders);
}
