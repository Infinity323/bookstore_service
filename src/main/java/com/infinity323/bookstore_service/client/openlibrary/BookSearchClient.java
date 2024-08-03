package com.infinity323.bookstore_service.client.openlibrary;

import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.infinity323.bookstore_service.domain.openlibrary.BookSearchResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * REST client to interface with the OpenLibrary book search API.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class BookSearchClient {

    private static final String BASE_URL = "https://openlibrary.org/search.json";

    private final RestTemplate restTemplate;

    /**
     * Searches for books by title.
     * 
     * @param title title
     * @return response body
     */
    public BookSearchResponse search(String title) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(BASE_URL)
                .queryParam("q", title);
        Long start = System.currentTimeMillis();
        log.info("Sending GET request to OpenLibrary book search API with params [{}]", title);
        HttpEntity<BookSearchResponse> responseEntity = restTemplate.getForEntity(builder.toUriString(),
                BookSearchResponse.class);
        log.info("OpenLibrary search completed in {} ms", System.currentTimeMillis() - start);
        return responseEntity.getBody();
    }
}
