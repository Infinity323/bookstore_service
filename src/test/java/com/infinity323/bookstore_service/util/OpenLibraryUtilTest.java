package com.infinity323.bookstore_service.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.infinity323.bookstore_service.domain.Book;
import com.infinity323.bookstore_service.domain.BookSearchResponse.Document;

class OpenLibraryUtilTest {

    @Test
    void mapBookDocuments_Success() {
        List<Document> documents = List.of(stubDocument());

        List<Book> books = OpenLibraryUtil.mapBookDocuments(documents);
        assertEquals(documents.get(0).getKey(), books.get(0).getOlKey());
        assertEquals(documents.get(0).getAuthorName().toString(), books.get(0).getAuthor());
    }

    @Test
    void mapBookDocument_Success() {
        Document document = stubDocument();

        Book book = OpenLibraryUtil.mapBookDocument(document);
        assertEquals(document.getKey(), book.getOlKey());
        assertEquals(document.getAuthorName().toString(), book.getAuthor());
    }

    private Document stubDocument() {
        Document document = new Document();
        document.setKey("test");
        document.setAuthorName(List.of("testAuthor"));
        return document;
    }

}
