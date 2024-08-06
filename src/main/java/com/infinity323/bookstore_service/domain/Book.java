package com.infinity323.bookstore_service.domain;

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
 * Entity class for books.
 */
@Entity
@Table(name = "book")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "ol_key", unique = true)
    private String olKey;

    @Column(name = "title")
    private String title;

    @Column(name = "author", length = 4000)
    private String author;

    @Column(name = "type")
    private String type;

    @Column(name = "first_publish_year")
    private Long firstPublishYear;

    @Column(name = "language")
    private String language;

}
