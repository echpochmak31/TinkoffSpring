package ru.tinkoff.edu.java.scrapper.services;


import jakarta.validation.constraints.Min;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.webclients.StackOverflowWebClient;
import ru.tinkoff.edu.java.scrapper.webclients.dto.StackOverflowApiResponse;

@Service
public class StackOverflowApiService {

    private final StackOverflowWebClient stackOverflowWebClient;

    public StackOverflowApiService(@Qualifier("stackOverflowWebClient") @NonNull StackOverflowWebClient webClient) {
        stackOverflowWebClient = webClient;
    }


    public StackOverflowApiResponse getQuestion(@NonNull @Min(0) Long questionId) {
        return stackOverflowWebClient.getQuestion(questionId);
    }

}
