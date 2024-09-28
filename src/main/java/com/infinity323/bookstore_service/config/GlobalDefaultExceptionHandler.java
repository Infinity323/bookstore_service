package com.infinity323.bookstore_service.config;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infinity323.bookstore_service.domain.ResponseDto;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
@Slf4j
public class GlobalDefaultExceptionHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDto> handleException(HttpServletRequest request, Exception e) throws Exception {
        log.error("Exception occurred while handling {} request at {} with params {}", request.getMethod(),
                request.getRequestURI(), objectMapper.writeValueAsString(request.getParameterMap()), e);

        ResponseDto responseDto = new ResponseDto();
        responseDto.setMessage("A server error occurred. Please try again.");
        responseDto.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
