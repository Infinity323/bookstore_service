package com.infinity323.bookstore_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infinity323.bookstore_service.domain.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByTitleContainsIgnoreCase(String title);

    Book findByOlKey(String olKey);

    boolean existsByOlKey(String olKey);

    Long deleteByOlKey(String olKey);

}
