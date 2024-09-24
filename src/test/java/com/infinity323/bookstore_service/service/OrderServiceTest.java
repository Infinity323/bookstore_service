package com.infinity323.bookstore_service.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.infinity323.bookstore_service.domain.Order;
import com.infinity323.bookstore_service.repository.OrderRepository;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @InjectMocks
    OrderService orderService;

    @Mock
    OrderRepository orderRepository;

    @Test
    void findOrdersByCustomerId_Exists_Success() {
        when(orderRepository.findByCustomer_PartyId(any())).thenReturn(List.of(new Order()));

        assertEquals(1, orderRepository.findByCustomer_PartyId("1").size());
    }

    @Test
    void findOrdersByCustomerId_NotExists_Success() {
        when(orderRepository.findByCustomer_PartyId(any())).thenReturn(List.of());

        assertTrue(orderService.findOrdersByCustomerPartyId("1").isEmpty());
    }

}
