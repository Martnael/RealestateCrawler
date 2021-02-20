package com.adaur.Crawler.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CrawlerErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CrawlerException.class)
    public ResponseEntity<CrawlerErrorResponse> handleError(CrawlerException exception) {
        CrawlerErrorResponse error = new CrawlerErrorResponse();
        error.setMessage(exception.getMessage());
        return new ResponseEntity<CrawlerErrorResponse>(error, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}
