package com.infinity323.bookstore_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infinity323.bookstore_service.domain.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
