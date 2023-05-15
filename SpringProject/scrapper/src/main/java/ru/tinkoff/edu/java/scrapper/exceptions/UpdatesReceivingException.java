package ru.tinkoff.edu.java.scrapper.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.tinkoff.edu.java.scrapper.dao.models.Link;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class UpdatesReceivingException extends ScrapperException {
    protected UpdatesReceivingException(String message) {
        super(message);
    }

    public static UpdatesReceivingException gitHubUpdatesFailure(Link link) {
        return new UpdatesReceivingException("Failed to receive updates for link: " + link);
    }

}
