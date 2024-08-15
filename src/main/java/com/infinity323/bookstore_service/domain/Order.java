package com.infinity323.bookstore_service.domain;

import java.time.ZonedDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity class for orders.
 */
@Entity
@Table(name = "book_order")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "order_number")
    private String orderNumber;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "paid")
    private Boolean isPaid;

    @Column(name = "payment_timestamp")
    private ZonedDateTime paymentTimestamp;

}
