package com.infinity323.bookstore_service.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.infinity323.bookstore_service.domain.Book;
import com.infinity323.bookstore_service.service.BookService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * API for books. Essentially a wrapper for OpenLibrary's book API.
 */
@RestController
@RequestMapping("api/v1/book")
@Api(tags = "Book API")
@RequiredArgsConstructor
@Slf4j
public class BookController {

    private final BookService bookService;

    /**
     * Endpoint to retrieve books from database.
     * 
     * @param title title
     * @return data
     */
    @GetMapping
    @ApiOperation(value = "Get Books", notes = "Endpoint to get books from database.")
    public List<Book> getBooks(@RequestParam(required = true) String title) {
        return bookService.getBooks(title);
    }

    /**
     * Endpoint to get book from database by OpenLibrary key.
     * 
     * @param olKey OpenLibrary key
     * @return data
     */
    @GetMapping("/{olKey}")
    @ApiOperation(value = "Get Book by OpenLibrary Key", notes = "Endpoint to get book from database by OpenLibrary key.")
    public Book getBookByOlKey(@PathVariable String olKey) {
        return bookService.getBookByOlKey(olKey);
    }

    /**
     * Endpoint to synchronize books table with OpenLibrary book search results by
     * title.
     * 
     * @param title title
     * @return data
     */
    @PostMapping("/sync")
    @ApiOperation(value = "Sync Books with OpenLibrary", notes = "Endpoint to sync database with OpenLibrary book search results by title.")
    public Map<String, List<Book>> synchronizeBooks(@RequestParam String title) {
        return bookService.synchronizeBooks(title);
    }

    /**
     * Endpoint to delete book from database by OpenLibrary key.
     * 
     * @param olKey OpenLibrary key
     * @return data
     */
    @DeleteMapping("/{olKey}")
    @ApiOperation(value = "Delete Book by OpenLibrary Key", notes = "Endpoint to delete book from database by OpenLibrary key.")
    public Long deleteBookByOlKey(@PathVariable String olKey) {
        return bookService.deleteBookByOlKey(olKey);
    }

}
