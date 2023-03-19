package ru.tinkoff.edu.java.parser;


import ru.tinkoff.edu.java.parser.handlers.GitHubLinkHandler;
import ru.tinkoff.edu.java.parser.handlers.LinkValidator;
import ru.tinkoff.edu.java.parser.handlers.StackOverflowLinkHandler;
import ru.tinkoff.edu.java.parser.results.GitHubParseResult;
import ru.tinkoff.edu.java.parser.results.StackOverflowParseResult;

public class Main {
    public static void main(String[] args) {
        var link1 = "https://github.com/sanyarnd/tinkoff-java-course-2022/sdgsg";
        var link2 = "https://stackoverflow.com/questions/1642028/what-is-the-operator-in-c";
        var link3 = "https://stackoverflow.com/search?q=unsupported%20link";

        var linkValidator = new LinkValidator();
        var gitHubHandler = new GitHubLinkHandler();
        var stackOverflowHandler = new StackOverflowLinkHandler();

        linkValidator.setNext(gitHubHandler);
        gitHubHandler.setNext(stackOverflowHandler);

        var linkParser = new DefaultLinkParser(linkValidator);

        var result1 = linkParser.parseLink(link1);
        var result2 = linkParser.parseLink(link2);
        var result3 = linkParser.parseLink(link3);

        if (result1 instanceof GitHubParseResult gitHubParseResult)
            System.out.println(gitHubParseResult);
        if (result2 instanceof StackOverflowParseResult stackOverflowParseResult)
            System.out.println(stackOverflowParseResult);
        if (result3 instanceof StackOverflowParseResult stackOverflowParseResult)
            System.out.println(stackOverflowParseResult);
        else System.out.println(result3);
    }
}
