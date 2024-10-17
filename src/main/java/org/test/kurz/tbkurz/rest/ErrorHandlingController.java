package org.test.kurz.tbkurz.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.test.kurz.tbkurz.exception.ResourceNotFoundException;

@Slf4j
@ControllerAdvice
public class ErrorHandlingController extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<Object> handleResourceNotFound(final ResourceNotFoundException ex, final WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleGeneralError(final Exception ex, final WebRequest request) {
        log.error("Hello there!", ex);
        return handleExceptionInternal(
                ex,
                "Congratulations! You managed to break it. Now go and check the logs, to see what went wrong.",
                new HttpHeaders(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                request);
    }

}
