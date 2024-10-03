package com.infinity323.bookstore_service.advice;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infinity323.bookstore_service.domain.ResponseDto;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalDefaultExceptionHandler {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ResponseDto> handleException(HttpServletRequest request, Exception e) throws Exception {
        if (Objects.nonNull(AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class))) {
            throw e;
        }

        log.error("Exception occurred while handling {} request at {} with params {}", request.getMethod(),
                request.getRequestURI(), objectMapper.writeValueAsString(request.getParameterMap()), e);

        ResponseDto responseDto = new ResponseDto();
        responseDto.setMessage(e.getMessage());
        responseDto.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
