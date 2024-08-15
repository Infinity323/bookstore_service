package com.infinity323.bookstore_service.controller;

import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.infinity323.bookstore_service.domain.Book;
import com.infinity323.bookstore_service.domain.ResponseDto;
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
    public ResponseEntity<ResponseDto> getBooks(@RequestParam(required = true) String title) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setData(bookService.getBooks(title));
        responseDto.setStatusCode(HttpStatus.OK);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    /**
     * Endpoint to get book from database by OpenLibrary key.
     * 
     * @param olKey OpenLibrary key
     * @return data
     */
    @GetMapping("/{olKey}")
    @ApiOperation(value = "Get Book by OpenLibrary Key", notes = "Endpoint to get book from database by OpenLibrary key.")
    public ResponseEntity<ResponseDto> getBookByOlKey(@PathVariable String olKey) {
        ResponseDto responseDto = new ResponseDto();
        Book book = bookService.getBookByOlKey(olKey);
        HttpStatus status = Objects.nonNull(book) ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        responseDto.setData(book);
        responseDto.setStatusCode(status);
        return new ResponseEntity<>(responseDto, status);
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
    public ResponseEntity<ResponseDto> synchronizeBooks(@RequestParam String title) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setData(bookService.synchronizeBooks(title));
        responseDto.setStatusCode(HttpStatus.OK);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    /**
     * Endpoint to delete book from database by OpenLibrary key.
     * 
     * @param olKey OpenLibrary key
     * @return data
     */
    @DeleteMapping("/{olKey}")
    @ApiOperation(value = "Delete Book by OpenLibrary Key", notes = "Endpoint to delete book from database by OpenLibrary key.")
    public ResponseEntity<ResponseDto> deleteBookByOlKey(@PathVariable String olKey) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setData(bookService.deleteBookByOlKey(olKey));
        responseDto.setStatusCode(HttpStatus.OK);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

}
