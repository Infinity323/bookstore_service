package com.infinity323.bookstore_service.book;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByTitleContainsIgnoreCase(String title);

    Book findByOlKey(String olKey);

    boolean existsByOlKey(String olKey);

    Long deleteByOlKey(String olKey);

}
