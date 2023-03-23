package ru.tinkoff.edu.java.scrapper;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import ru.tinkoff.edu.java.scrapper.dto.ApiErrorResponse;
import ru.tinkoff.edu.java.scrapper.exceptions.ResourceNotFoundException;

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

    @ExceptionHandler(value = ResourceNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ApiErrorResponse handleNotFound(ResourceNotFoundException exception, WebRequest request) {

        return new ApiErrorResponse(
                request.getDescription(false),
                HttpStatus.NOT_FOUND.toString() + " shit",
                exception.getClass().getName(),
                exception.getMessage(),
                Arrays.stream(exception.getStackTrace()).map(Objects::toString).toArray(String[]::new));
    }

}
