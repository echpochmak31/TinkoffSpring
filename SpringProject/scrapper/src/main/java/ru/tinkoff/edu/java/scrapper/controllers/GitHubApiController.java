package ru.tinkoff.edu.java.scrapper.controllers;


import jakarta.validation.constraints.Min;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.edu.java.scrapper.services.GitHubApiService;
import ru.tinkoff.edu.java.scrapper.webclients.dto.GitHubApiResponse;
import ru.tinkoff.edu.java.scrapper.webclients.dto.StackOverflowApiResponse;

@RestController
@RequestMapping("/api/github")
public class GitHubApiController {

    private final GitHubApiService gitHubApiService;

    public GitHubApiController(GitHubApiService gitHubApiService) {
        this.gitHubApiService = gitHubApiService;
    }

    @GetMapping("/repos/{owner}/{repo}")
    public GitHubApiResponse getRepository(@PathVariable("owner") String owner, @PathVariable("repo") String repo) {
        return gitHubApiService.getRepository(owner, repo);
    }
}
