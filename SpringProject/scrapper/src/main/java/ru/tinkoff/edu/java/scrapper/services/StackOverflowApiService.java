package ru.tinkoff.edu.java.scrapper.services;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.edu.java.scrapper.webclients.dto.StackOverflowApiResponse;

import java.beans.ConstructorProperties;

@Service
public class StackOverflowApiService {

    public StackOverflowApiService(@Qualifier("stackOverflowWebClient") WebClient webClient){
        this.webClient = webClient;
    }

    private final WebClient webClient;

    public StackOverflowApiResponse getQuestion(Long questionId) {

        return webClient
                .get()
                .uri(String.join(
                        "",
                        "https://api.stackexchange.com/2.3/questions/",
                        questionId.toString(),
                        "?order=desc&sort=activity&site=stackoverflow"))
                .retrieve()
                .bodyToMono(StackOverflowApiResponse.class)
                .block();
    }

}
