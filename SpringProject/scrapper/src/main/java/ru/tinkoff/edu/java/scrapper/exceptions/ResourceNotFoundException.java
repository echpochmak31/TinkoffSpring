package ru.tinkoff.edu.java.scrapper.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends ScrapperException {
    private ResourceNotFoundException(String message) {
        super(message);
    }

    public static ResourceNotFoundException chatNotFound(long tgChatId) {
        return new ResourceNotFoundException("Chat " + tgChatId + " not found.");
    }
}
