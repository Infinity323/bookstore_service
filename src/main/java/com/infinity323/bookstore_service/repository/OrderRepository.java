package com.infinity323.bookstore_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infinity323.bookstore_service.domain.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByCustomerId(Long customerId);

    Order findByOrderNumber(String orderNumber);

}
