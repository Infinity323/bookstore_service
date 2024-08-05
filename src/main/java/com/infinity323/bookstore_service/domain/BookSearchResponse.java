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
        @JsonProperty("cover_i")
        private Integer coverI;
        @JsonProperty("has_fulltext")
        private Boolean hasFullText;
        @JsonProperty("edition_count")
        private Integer editionCount;
        @JsonProperty("title")
        private String title;
        @JsonProperty("author_name")
        private List<String> authorName;
        @JsonProperty("first_publish_year")
        private String firstPublishYear;
        @JsonProperty("key")
        private String key;
        @JsonProperty("ia")
        private List<String> ia;
        @JsonProperty("author_key")
        private List<String> authorKey;
        @JsonProperty("public_scan_b")
        private Boolean publicScanB;
    }
}
