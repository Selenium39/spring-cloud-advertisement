package com.selenium.ad.advice;

import com.selenium.ad.exception.AdException;
import com.selenium.ad.vo.CommonResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * 统一异常处理
 */
@RestControllerAdvice
public class GlobalExceptionAdvice {
    //只要代码中抛出了AdException,就能被此方法捕获到
    @ExceptionHandler(value = AdException.class)
    public CommonResponse<String> handleAdException(HttpServletRequest req, AdException ex) {
        CommonResponse<String> response = new CommonResponse<>(-1, "business error");
        response.setData(ex.getMessage());
        return response;
    }
}
