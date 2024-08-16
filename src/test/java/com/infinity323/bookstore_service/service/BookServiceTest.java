package com.infinity323.bookstore_service.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.infinity323.bookstore_service.domain.Book;
import com.infinity323.bookstore_service.repository.BookRepository;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @InjectMocks
    BookService bookService;

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

}
