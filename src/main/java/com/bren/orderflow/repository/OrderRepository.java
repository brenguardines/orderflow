package com.bren.orderflow.repository;

import com.bren.orderflow.model.entity.Order;
import com.bren.orderflow.model.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByStatus(OrderStatus status);
}