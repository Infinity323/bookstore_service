package com.infinity323.bookstore_service.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PerformanceInterceptor implements HandlerInterceptor {

    private static final String START = "start";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        log.info("Received {} request at {}", request.getMethod(), request.getRequestURI());
        request.setAttribute(START, System.currentTimeMillis());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) {
        Long start = (Long) request.getAttribute(START);
        log.info("Completed {} request at {} in {} ms", request.getMethod(), request.getRequestURI(),
                System.currentTimeMillis() - start);
    }

}
