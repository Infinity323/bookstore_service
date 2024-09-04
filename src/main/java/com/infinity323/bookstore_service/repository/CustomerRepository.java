package com.infinity323.bookstore_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infinity323.bookstore_service.domain.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
