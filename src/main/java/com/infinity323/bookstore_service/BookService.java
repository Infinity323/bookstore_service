package com.infinity323.bookstore_service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.infinity323.bookstore_service.client.OpenLibraryBookSearchClient;
import com.infinity323.bookstore_service.util.OpenLibraryUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {

    private final OpenLibraryBookSearchClient bookSearchClient;

    public List<Book> getBooks(String title) {
        BookSearchResponse openLibraryResponse = bookSearchClient.search(title);
        return OpenLibraryUtil.mapDocuments(openLibraryResponse.getDocs());
    }

}
