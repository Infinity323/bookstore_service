package com.infinity323.bookstore_service.controller;

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
import org.springframework.http.HttpStatus;

import com.infinity323.bookstore_service.domain.Book;
import com.infinity323.bookstore_service.domain.ResponseDto;
import com.infinity323.bookstore_service.service.BookService;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {

    @InjectMocks
    BookController bookController;

    @Mock
    BookService bookService;

    @Test
    @SuppressWarnings("unchecked")
    void getBooks_Success() {
        when(bookService.getBooks(any())).thenReturn(List.of(new Book()));

        ResponseDto responseDto = bookController.getBooks("test").getBody();
        assertNotNull(responseDto);
        assertEquals(1, ((List<Book>) responseDto.getData()).size());
        assertEquals(HttpStatus.OK, HttpStatus.valueOf(responseDto.getStatus()));
    }

    @Test
    @SuppressWarnings("unchecked")
    void getBooks_NotFound() {
        when(bookService.getBooks(any())).thenReturn(List.of());

        ResponseDto responseDto = bookController.getBooks("test").getBody();
        assertNotNull(responseDto);
        assertTrue(((List<Book>) responseDto.getData()).isEmpty());
        assertEquals(HttpStatus.NOT_FOUND, HttpStatus.valueOf(responseDto.getStatus()));
    }

    @Test
    void getBookByOlKey_Success() {
        when(bookService.getBookByOlKey(any())).thenReturn(new Book());

        ResponseDto responseDto = bookController.getBookByOlKey("test").getBody();
        assertNotNull(responseDto);
        assertNotNull(responseDto.getData());
        assertEquals(HttpStatus.OK, HttpStatus.valueOf(responseDto.getStatus()));
    }

    @Test
    void getBookByOlKey_NotFound() {
        when(bookService.getBookByOlKey(any())).thenReturn(null);

        ResponseDto responseDto = bookController.getBookByOlKey("test").getBody();
        assertNotNull(responseDto);
        assertNull(responseDto.getData());
        assertEquals(HttpStatus.NOT_FOUND, HttpStatus.valueOf(responseDto.getStatus()));
    }

    @Test
    void deleteBookByOlKey_Success() {
        when(bookService.deleteBookByOlKey(any())).thenReturn(1L);

        ResponseDto responseDto = bookController.deleteBookByOlKey("test").getBody();
        assertNotNull(responseDto);
        assertEquals(1L, responseDto.getData());
        assertEquals(HttpStatus.OK, HttpStatus.valueOf(responseDto.getStatus()));
    }

    @Test
    void deleteBookByOlKey_NotFound() {
        when(bookService.deleteBookByOlKey(any())).thenReturn(0L);

        ResponseDto responseDto = bookController.deleteBookByOlKey("test").getBody();
        assertNotNull(responseDto);
        assertEquals(0L, responseDto.getData());
        assertEquals(HttpStatus.OK, HttpStatus.valueOf(responseDto.getStatus()));
    }

}
