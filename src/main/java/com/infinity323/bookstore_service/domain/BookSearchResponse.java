package com.infinity323.bookstore_service.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookSearchResponse {
    private Integer numFound;
    private Integer start;
    private Boolean numFoundExact;
    private List<Document> docs;

    @Data
    public static class Document {
        @JsonProperty("author_name")
        private List<String> authorName;
        @JsonProperty("first_publish_year")
        private Long firstPublishYear;
        @JsonProperty("key")
        private String key;
        @JsonProperty("language")
        private List<String> language;
        @JsonProperty("title")
        private String title;
        @JsonProperty("type")
        private String type;
    }
}
