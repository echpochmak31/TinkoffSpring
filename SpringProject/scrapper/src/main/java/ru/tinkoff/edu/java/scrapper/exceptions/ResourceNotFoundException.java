package ru.tinkoff.edu.java.scrapper.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class ResourceNotFoundException extends ScrapperException {
    protected ResourceNotFoundException(String message) {
        super(message);
    }

    public static ResourceNotFoundException chatNotFound(long tgChatId) {
        return new ResourceNotFoundException("Chat " + tgChatId + " not found.");
    }

    public static ResourceNotFoundException linkNotFound(long tgChatId, String url) {
        return new ResourceNotFoundException("In chat " + tgChatId + "link " + url + " not found.");
    }
}
