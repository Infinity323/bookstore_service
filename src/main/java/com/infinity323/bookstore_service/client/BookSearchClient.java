package com.infinity323.bookstore_service.client;

import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.infinity323.bookstore_service.domain.BookSearchResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * REST client to interface with the OpenLibrary book search API.
 * 
 * @see <a href="https://openlibrary.org/dev/docs/api/search">OpenLibrary API
 *      documentation</a>
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class BookSearchClient {

    private static final String BASE_URL = "https://openlibrary.org/search.json";
    private static final String VALUES_TO_RETURN = "key,title,author_name,type,first_publish_year,language";
    private static final int RESPONSE_SIZE_LIMIT = 50;

    private final RestTemplate restTemplate;

    /**
     * Searches for books by title.
     * 
     * @param title title
     * @return response body
     */
    public BookSearchResponse search(String title) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(BASE_URL)
                .queryParam("q", title)
                .queryParam("field", VALUES_TO_RETURN)
                .queryParam("limit", RESPONSE_SIZE_LIMIT);
        Long start = System.currentTimeMillis();
        log.info("Sending GET request to OpenLibrary book search API [{}]", builder.toUriString());
        HttpEntity<BookSearchResponse> responseEntity = restTemplate.getForEntity(builder.toUriString(),
                BookSearchResponse.class);
        log.info("OpenLibrary search completed in {} ms", System.currentTimeMillis() - start);
        return responseEntity.getBody();
    }
}
