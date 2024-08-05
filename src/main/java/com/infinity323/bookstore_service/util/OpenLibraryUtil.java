package com.infinity323.bookstore_service.util;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.infinity323.bookstore_service.domain.Book;
import com.infinity323.bookstore_service.domain.BookSearchResponse.Document;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class OpenLibraryUtil {

    private OpenLibraryUtil() {
    }

    public static List<Book> mapBookDocuments(List<Document> documents) {
        return documents.stream()
                .map(d -> mapBookDocument(d))
                .collect(Collectors.toList());
    }

    public static Book mapBookDocument(Document document) {
        return Book.builder()
                .olKey(document.getKey())
                .author(Objects.toString(document.getAuthorName(), null))
                .title(document.getTitle())
                .build();
    }

}
