package com.infinity323.bookstore_service.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.infinity323.bookstore_service.domain.Customer;
import com.infinity323.bookstore_service.domain.Order;

@DataJpaTest
class OrderRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private OrderRepository orderRepository;

    @BeforeEach
    void setUp() {
        stubOrders();
    }

    @AfterEach
    void tearDown() {
        orderRepository.deleteAll();
        customerRepository.deleteAll();
    }

    @Test
    void findByCustomerId_OrdersExists_Success() {
        assertEquals(2, orderRepository.findByCustomer_PartyId("1").size());
    }

    @Test
    void findByCustomerId_OrdersDoNotExist_Success() {
        assertTrue(orderRepository.findByCustomer_PartyId("3").isEmpty());
    }

    private void stubOrders() {
        Customer customer1 = customerRepository.save(Customer.builder().partyId("1").build());
        Customer customer2 = customerRepository.save(Customer.builder().partyId("2").build());
        Order order1 = Order.builder().orderNumber("1").customer(customer1).build();
        Order order2 = Order.builder().orderNumber("2").customer(customer1).build();
        Order order3 = Order.builder().orderNumber("3").customer(customer2).build();
        List<Order> orders = List.of(order1, order2, order3);
        orderRepository.saveAll(orders);
    }
}
