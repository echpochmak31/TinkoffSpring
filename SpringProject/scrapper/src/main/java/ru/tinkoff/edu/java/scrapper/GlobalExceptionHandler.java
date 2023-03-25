package ru.tinkoff.edu.java.scrapper;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.tinkoff.edu.java.scrapper.dto.ApiErrorResponse;
import ru.tinkoff.edu.java.scrapper.exceptions.ResourceNotFoundException;

import java.util.Arrays;
import java.util.Objects;

@Component
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleInvalidArgsRequest(MethodArgumentNotValidException exception, WebRequest request) {
        return createApiErrorResponse(exception, request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleBadRequest(HttpMessageNotReadableException exception, WebRequest request) {
        return createApiErrorResponse(exception, request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ResourceNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ApiErrorResponse handleNotFound(ResourceNotFoundException exception, WebRequest request) {
        return createApiErrorResponse(exception, request, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleInvalidPathVariables(ConstraintViolationException exception, WebRequest request) {
        return createApiErrorResponse(exception, request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleArgumentTypeMismatch(MethodArgumentTypeMismatchException exception, WebRequest request) {
        return createApiErrorResponse(exception, request, HttpStatus.BAD_REQUEST);
    }

    private static ApiErrorResponse createApiErrorResponse(Exception exception, WebRequest request, HttpStatus status) {
        return new ApiErrorResponse(
                request.getDescription(false),
                status.toString(),
                exception.getClass().getName(),
                exception.getMessage(),
                Arrays.stream(exception.getStackTrace()).map(Objects::toString).toArray(String[]::new));
    }
}
