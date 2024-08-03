package com.infinity323.bookstore_service.book;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.infinity323.bookstore_service.domain.ResponseDto;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * API for books.
 */
@RestController
@RequestMapping("api/v1/books")
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
    @ApiOperation(value = "Get Books", notes = "Endpoint to get books from database")
    public ResponseEntity<ResponseDto> getBooks(@RequestParam(required = false) String title) {
        ResponseDto responseDto = new ResponseDto();
        try {
            responseDto.setData(bookService.getBooks(title));
            responseDto.setStatusCode(HttpStatus.OK);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception occurred while getting books", e);
            responseDto.setMessage(e.getMessage());
            responseDto.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Endpoint to synchronize database with OpenLibrary book search results by
     * title.
     * 
     * @param title title
     * @return data
     */
    @PostMapping("/sync")
    @ApiOperation(value = "Sync DB with OpenLibrary", notes = "Endpoint to sync database with OpenLibrary book search results by title.")
    public ResponseEntity<ResponseDto> postMethodName(@RequestParam String title) {
        ResponseDto responseDto = new ResponseDto();
        try {
            responseDto.setData(bookService.synchronizeBooks(title));
            responseDto.setStatusCode(HttpStatus.OK);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception occurred while synchronizing database", e);
            responseDto.setMessage(e.getMessage());
            responseDto.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
