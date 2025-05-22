package com.lake.hmediacenterserver.common;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Hidden
@RestControllerAdvice(basePackages = {"com.lake.hmediacenterserver"})
public class GlobalExceptionHandler {

    @ExceptionHandler(BizException.class)
    public ApiResponse<Void> handleBizException(BizException ex) {
        return ApiResponse.fail(ex.getCode(), ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<Void> handleException(HttpServletRequest req, Exception ex) {
        return ApiResponse.fail(500, ex.getMessage());
    }
}