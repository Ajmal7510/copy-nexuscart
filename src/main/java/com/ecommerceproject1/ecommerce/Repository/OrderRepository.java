package com.ecommerceproject1.ecommerce.Repository;

import com.ecommerceproject1.ecommerce.Entity.user.Orders;
import com.ecommerceproject1.ecommerce.Entity.user.UserInfo;
import com.ecommerceproject1.ecommerce.model.product.OrderAmountDto;
import io.lettuce.core.dynamic.annotation.Param;
import org.hibernate.query.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders,Long> {

    List<Orders> findByUser(UserInfo user);
    List<Orders> findByOrderByOrderDateDesc();

    List<Orders> findByUserOrderByOrderIdDesc(UserInfo user);

    @Query("SELECT SUM(o.totalAmount) FROM Orders o")
    double calculateTotalRevenue();

    @Query("SELECT COUNT(DISTINCT o.user) FROM Orders o")
    long getTotalUserCount();

    @Query("SELECT COUNT(p) FROM Products p")
    long getTotalProductCount();

//    @Query("SELECT o.totalAmount FROM Orders o WHERE o.orderDate BETWEEN :startDate AND :endDate")
//    List<Double> findTotalAmountsForLast7Days(LocalDate startDate, LocalDate endDate);

    @Query("SELECT NEW com.ecommerceproject1.ecommerce.model.product.OrderAmountDto(o.orderDate, SUM(o.totalAmount)) " +
            "FROM Orders o " +
            "WHERE o.status = 'Delivered' " +
            "AND o.orderDate BETWEEN :startDate AND :endDate " +
            "GROUP BY o.orderDate")
    List<OrderAmountDto> findTotalAmountsByDateRangeAndPaymentStatus(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT COALESCE(SUM(o.totalAmount), 0) FROM Orders o WHERE o.deliveredDate = :deliveredDate")
    double getTotalOrderAmountByDeliveredDate(@Param("deliveredDate") LocalDate deliveredDate);
}
