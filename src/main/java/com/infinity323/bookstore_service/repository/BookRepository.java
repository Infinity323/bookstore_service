package com.infinity323.bookstore_service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infinity323.bookstore_service.domain.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByTitleContainsIgnoreCase(String title);

    Optional<Book> findByOlKey(String olKey);

    boolean existsByOlKey(String olKey);

    Long deleteByOlKey(String olKey);

    List<Long> findIdByOlKeyNotIn(List<String> olKeys);
}
