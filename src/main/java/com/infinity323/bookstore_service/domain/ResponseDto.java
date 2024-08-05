package com.infinity323.bookstore_service.domain;

import org.springframework.http.HttpStatus;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseDto {
    private Object data;
    private String message;
    private Integer status;
    private Boolean success;

    public void setStatusCode(HttpStatus httpStatus) {
        setStatus(httpStatus.value());
        setSuccess(httpStatus.is2xxSuccessful());
    }
}
