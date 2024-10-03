package com.infinity323.bookstore_service.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.infinity323.bookstore_service.client.BookSearchClient;
import com.infinity323.bookstore_service.domain.Book;
import com.infinity323.bookstore_service.domain.BookSearchResponse;
import com.infinity323.bookstore_service.domain.BookSearchResponse.Document;
import com.infinity323.bookstore_service.repository.BookRepository;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @InjectMocks
    BookService bookService;
    @Mock
    BookSearchClient bookSearchClient;
    @Mock
    BookRepository bookRepository;

    @Test
    void getBooks_Exists_Success() {
        when(bookRepository.findByTitleContainsIgnoreCase(any())).thenReturn(List.of(new Book()));

        assertEquals(1, bookService.getBooks("test").size());
    }

    @Test
    void getBooks_NotExists_Success() {
        when(bookRepository.findByTitleContainsIgnoreCase(any())).thenReturn(List.of());

        assertTrue(bookService.getBooks("test").isEmpty());
    }

    @Test
    void getBookByOlKey_Exists_Success() {
        when(bookRepository.findByOlKey(any())).thenReturn(new Book());

        assertNotNull(bookService.getBookByOlKey("test"));
    }

    @Test
    void getBookByOlKey_NotExists_Success() {
        when(bookRepository.findByOlKey(any())).thenReturn(null);

        assertNull(bookService.getBookByOlKey("test"));
    }

    @Test
    void deleteBookByOlKey_Exists_Success() {
        when(bookRepository.deleteByOlKey(any())).thenReturn(1L);

        assertEquals(1L, bookService.deleteBookByOlKey("test"));
    }

    @Test
    void deleteBookByOlKey_NotExists_Success() {
        when(bookRepository.deleteByOlKey(any())).thenReturn(0L);

        assertEquals(0L, bookService.deleteBookByOlKey("test"));
    }

    @Test
    void synchronizeBooks_AlreadySynced() {
        when(bookSearchClient.searchByTitle(any())).thenReturn(new BookSearchResponse());

        Map<String, Integer> results = bookService.synchronizeBooks("test");

        assertEquals(0, results.get("saved"));
        assertEquals(0, results.get("deleted"));
    }

    @Test
    void synchronizeBooks_SaveOne() {
        when(bookSearchClient.searchByTitle(any())).thenReturn(stubBookSearchResponse());
        when(bookRepository.existsByOlKey("key1")).thenReturn(false);
        when(bookRepository.existsByOlKey("key2")).thenReturn(true);

        Map<String, Integer> results = bookService.synchronizeBooks("test");

        assertEquals(1, results.get("saved"));
        assertEquals(0, results.get("deleted"));
    }

    @Test
    void synchronizeBooks_DeleteOne() {
        when(bookSearchClient.searchByTitle(any())).thenReturn(stubBookSearchResponse());
        when(bookRepository.existsByOlKey(any())).thenReturn(true);
        when(bookRepository.findIdByTitleAndOlKeyNotIn(any(), any())).thenReturn(List.of(1L));

        Map<String, Integer> results = bookService.synchronizeBooks("test");

        assertEquals(0, results.get("saved"));
        assertEquals(1, results.get("deleted"));
    }

    private BookSearchResponse stubBookSearchResponse() {
        Document document1 = new Document();
        document1.setKey("key1");
        Document document2 = new Document();
        document2.setKey("key2");
        BookSearchResponse bookSearchResponse = new BookSearchResponse();
        bookSearchResponse.setDocs(List.of(document1, document2));
        return bookSearchResponse;
    }

}
