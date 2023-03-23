package ru.tinkoff.edu.java.bot;

import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.tinkoff.edu.java.bot.dto.ApiErrorResponse;

import java.util.Arrays;
import java.util.Objects;

@Component
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ApiErrorResponse handleInvalidArgsRequest(MethodArgumentNotValidException exception, WebRequest request) {

        return new ApiErrorResponse(
                request.getDescription(false),
                HttpStatus.BAD_REQUEST.toString(),
                exception.getClass().getName(),
                exception.getMessage(),
                Arrays.stream(exception.getStackTrace()).map(Objects::toString).toArray(String[]::new));
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ApiErrorResponse handleBadRequest(HttpMessageNotReadableException exception, WebRequest request) {

        return new ApiErrorResponse(
                request.getDescription(false),
                HttpStatus.BAD_REQUEST.toString(),
                exception.getClass().getName(),
                exception.getMessage(),
                Arrays.stream(exception.getStackTrace()).map(Objects::toString).toArray(String[]::new));
    }
}
