package com.infinity323.bookstore_service.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.test.web.servlet.MockMvc;

import com.infinity323.bookstore_service.domain.Book;
import com.infinity323.bookstore_service.service.BookService;

@WebMvcTest(BookController.class)
class BookControllerTest {

    private static String BASE_URL = "/api/v1/book";

    @Autowired
    MockMvc mockMvc;
    @MockBean
    BookService bookService;

    @Test
    void getBooks_Success() throws Exception {
        when(bookService.getBooks(any())).thenReturn(List.of(new Book()));

        mockMvc.perform(get(BASE_URL).param("title", "test"))
                .andExpect(status().isOk());
    }

    @Test
    void getBookByOlKey_Success() throws Exception {
        when(bookService.getBookByOlKey(any())).thenReturn(new Book());

        mockMvc.perform(get(BASE_URL + "/test"))
                .andExpect(status().isOk());
    }

    @Test
    void getBookByOlKey_NotFound() throws Exception {
        when(bookService.getBookByOlKey(any())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(BASE_URL + "/test"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteBookByOlKey_Success() throws Exception {
        when(bookService.deleteBookByOlKey(any())).thenReturn(1L);

        mockMvc.perform(delete(BASE_URL + "/test"))
                .andExpect(status().isOk());
    }
}
