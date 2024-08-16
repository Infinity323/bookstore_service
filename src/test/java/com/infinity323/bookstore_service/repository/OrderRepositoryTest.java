package com.infinity323.bookstore_service.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.infinity323.bookstore_service.domain.Order;

@DataJpaTest
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    void findByCustomerId_OrdersExists_Success() {
        stubOrders();

        assertEquals(2, orderRepository.findByCustomerId(1L).size());
    }

    @Test
    void findByCustomerId_OrdersDoNotExist_Success() {
        stubOrders();

        assertTrue(orderRepository.findByCustomerId(3L).isEmpty());
    }

    private void stubOrders() {
        Order order1 = Order.builder().customerId(1L).build();
        Order order2 = Order.builder().customerId(1L).build();
        Order order3 = Order.builder().customerId(2L).build();
        List<Order> orders = List.of(order1, order2, order3);
        orderRepository.saveAll(orders);
    }
}
