package ru.tinkoff.edu.java.scrapper.controllers;

import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.edu.java.scrapper.services.StackOverflowApiService;
import ru.tinkoff.edu.java.scrapper.webclients.dto.StackOverflowApiResponse;

@RestController
@RequestMapping("/api/stackoverflow")
public class StackOverflowApiController {

    @Autowired
    private StackOverflowApiService stackOverflowService;
    @GetMapping("/questions/{id}")
    public StackOverflowApiResponse getQuestion(@PathVariable("id") @Min(0) long questionId) {
        return stackOverflowService.getQuestion(questionId);
    }
}
