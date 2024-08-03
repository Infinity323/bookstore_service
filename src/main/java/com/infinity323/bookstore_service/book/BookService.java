package com.infinity323.bookstore_service.book;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.infinity323.bookstore_service.client.openlibrary.BookSearchClient;
import com.infinity323.bookstore_service.domain.openlibrary.BookSearchResponse;
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
    public Book getBook(String olKey) {
        Book book = bookRepository.findByOlKey(olKey);
        log.info("{} with OpenLibrary key \"{}\"", Objects.nonNull(book) ? "Book found" : "No book found", olKey);
        return book;
    }

    /**
     * Synchronizes books searched by title from OpenLibrary with the database.
     * 
     * @param title title
     * @return number of books saved
     */
    public int synchronizeBooks(String title) {
        BookSearchResponse openLibraryResponse = bookSearchClient.search(title);
        List<Book> booksToSave = openLibraryResponse.getDocs().stream()
                .filter(d -> !bookRepository.existsByOlKey(d.getKey()))
                .map(d -> OpenLibraryUtil.mapBookDocument(d))
                .collect(Collectors.toList());
        bookRepository.saveAll(booksToSave);
        log.info("Saved {} books to the database based on search by title {}", booksToSave.size(), title);
        return booksToSave.size();
    }

}
