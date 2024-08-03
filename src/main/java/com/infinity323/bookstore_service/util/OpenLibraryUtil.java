package com.infinity323.bookstore_service.util;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.infinity323.bookstore_service.book.Book;
import com.infinity323.bookstore_service.domain.openlibrary.BookSearchResponse.Document;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class OpenLibraryUtil {

    private OpenLibraryUtil() {
    }

    public static List<Book> mapDocuments(List<Document> documents) {
        return documents.stream()
                .map(d -> Book.builder()
                        .author(Objects.toString(d.getAuthorName(), null))
                        .title(d.getTitle())
                        .build())
                .collect(Collectors.toList());
    }

}
