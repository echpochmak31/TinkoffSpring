package ru.tinkoff.edu.java.scrapper.controllers;

import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.edu.java.scrapper.services.StackOverflowService;
import ru.tinkoff.edu.java.scrapper.webclients.dto.StackOverflowResponse;

@RestController
@RequestMapping("/stackoverflow/api")
public class StackOverflowApiController {

    @Autowired
    private StackOverflowService stackOverflowService;
    @GetMapping("/questions/{id}")
    public StackOverflowResponse getQuestion(@PathVariable("id") @Min(0) long questionId) {
        return stackOverflowService.getQuestion(questionId);
    }
}
