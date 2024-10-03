package com.infinity323.bookstore_service.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.infinity323.bookstore_service.client.BookSearchClient;
import com.infinity323.bookstore_service.domain.Book;
import com.infinity323.bookstore_service.domain.BookSearchResponse;
import com.infinity323.bookstore_service.domain.BookSearchResponse.Document;
import com.infinity323.bookstore_service.repository.BookRepository;
import com.infinity323.bookstore_service.util.OpenLibraryUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {

    private final BookRepository bookRepository;

    private final BookSearchClient bookSearchClient;

    /**
     * Gets books from the database.
     * 
     * @param title title
     * @return list of books matching search criteria
     */
    public List<Book> getBooks(String title) {
        List<Book> books = bookRepository.findByTitleContainsIgnoreCase(title);
        log.info("Retrieved {} books with title containing \"{}\" from the database", books.size(), title);
        return books;
    }

    /**
     * Gets book from the database with OpenLibrary key.
     * 
     * @param olKey OpenLibrary key
     * @return book
     */
    public Book getBookByOlKey(String olKey) {
        Book book = bookRepository.findByOlKey(olKey);
        log.info("{} with OpenLibrary key \"{}\"", Objects.nonNull(book) ? "Book found" : "No book found", olKey);
        return book;
    }

    /**
     * Synchronizes books searched by title from OpenLibrary with the database.
     * Saves books from OpenLibrary that are not currently in the database
     * and deletes books from the database that were not returned from OpenLibrary.
     * 
     * @param title title
     * @return number of books saved and deleted
     */
    public Map<String, Integer> synchronizeBooks(String title) {
        Map<String, Integer> results = new HashMap<>();
        BookSearchResponse openLibraryResponse = bookSearchClient.searchByTitle(title);
        List<Book> booksToSave = openLibraryResponse.getDocs().stream()
                .filter(d -> !bookRepository.existsByOlKey(d.getKey()))
                .map(OpenLibraryUtil::mapBookDocument)
                .collect(Collectors.toList());
        bookRepository.saveAll(booksToSave);
        log.info("Saved {} books to the database based on search by title {}", booksToSave.size(), title);
        results.put("saved", booksToSave.size());
        List<Long> bookIdsToDelete = bookRepository.findIdByTitleAndOlKeyNotIn(
                title,
                openLibraryResponse.getDocs().stream()
                        .map(Document::getKey)
                        .collect(Collectors.toList()));
        bookRepository.deleteAllById(bookIdsToDelete);
        log.info("Deleted {} books from the database based on search by title {}", bookIdsToDelete.size(), title);
        results.put("deleted", bookIdsToDelete.size());
        return results;
    }

    /**
     * Deletes book from database with matching OpenLibrary key.
     * 
     * @param olKey OpenLibrary key
     * @return number of books deleted
     */
    public Long deleteBookByOlKey(String olKey) {
        Long numDeleted = bookRepository.deleteByOlKey(olKey);
        log.info("Deleted {} books from the database with OpenLibrary key \"{}\"", numDeleted, olKey);
        return numDeleted;
    }

}
