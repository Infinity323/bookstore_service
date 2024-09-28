package com.infinity323.bookstore_service.config;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infinity323.bookstore_service.domain.ResponseDto;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE - 1)
@Slf4j
public class GlobalExceptionHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseDto> handleIllegalArgumentException(HttpServletRequest request, Exception e)
            throws Exception {
        logError(request, e);
        return new ResponseEntity<>(getBody(HttpStatus.BAD_REQUEST, e), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseDto> handleResourceNotFoundException(HttpServletRequest request, Exception e)
            throws Exception {
        logError(request, e);
        return new ResponseEntity<>(getBody(HttpStatus.NOT_FOUND, e), HttpStatus.NOT_FOUND);
    }

    private void logError(HttpServletRequest request, Exception e) throws JsonProcessingException {
        log.error("{} occurred while handling {} request at {} with params {}: {}",
                e.getClass().getSimpleName(), request.getMethod(), request.getRequestURI(),
                objectMapper.writeValueAsString(request.getParameterMap()), e.getMessage());
    }

    private ResponseDto getBody(HttpStatus status, Exception e) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setMessage(e.getMessage());
        responseDto.setStatusCode(status);
        return responseDto;
    }
}
