package com.infinity323.bookstore_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infinity323.bookstore_service.domain.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByCustomer_PartyId(String partyId);

    Order findByOrderNumber(String orderNumber);

}
