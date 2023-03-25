package ru.tinkoff.edu.java.scrapper.services;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.edu.java.scrapper.webclients.dto.StackOverflowResponse;

@Service
@AllArgsConstructor
public class StackOverflowService {
    private final WebClient webClient;

    public StackOverflowResponse getQuestion(Long questionId) {

        return webClient
                .get()
                .uri(String.join(
                        "",
                        "https://api.stackexchange.com/2.3/questions/",
                        questionId.toString(),
                        "?order=desc&sort=activity&site=stackoverflow"))
                .retrieve()
                .bodyToMono(StackOverflowResponse.class)
                .block();
    }

}
