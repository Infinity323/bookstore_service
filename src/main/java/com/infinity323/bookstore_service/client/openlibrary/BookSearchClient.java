package com.infinity323.bookstore_service.client.openlibrary;

import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.infinity323.bookstore_service.domain.openlibrary.BookSearchResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BookSearchClient {

    private final RestTemplate restTemplate;

    public BookSearchResponse search(String title) {
        String url = "https://openlibrary.org/search.json";
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
                .queryParam("q", title);
        HttpEntity<BookSearchResponse> responseEntity = restTemplate.getForEntity(builder.toUriString(),
                BookSearchResponse.class);
        return responseEntity.getBody();
    }
}
