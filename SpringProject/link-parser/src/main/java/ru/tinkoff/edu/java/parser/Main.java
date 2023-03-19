package ru.tinkoff.edu.java.parser;


import org.apache.commons.validator.routines.UrlValidator;
import ru.tinkoff.edu.java.parser.handlers.DefaultUrlChecker;
import ru.tinkoff.edu.java.parser.handlers.Handler;
import ru.tinkoff.edu.java.parser.handlers.LinkHandler;
import ru.tinkoff.edu.java.parser.handlers.LinkValidator;
import ru.tinkoff.edu.java.parser.results.StackOverflowParseResult;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws IOException {
        StackOverflowParseResult stackOverflowParseResult;
        Handler h1 = new LinkValidator(new DefaultUrlChecker(new UrlValidator()));
        try {
            Handler h2 = new LinkHandler<>(Main::isValidURL, StackOverflowParseResult.class);
            h1.setNext(h2);
        }
        catch (Exception exception) {
            System.out.println(exception);
        }

        String link = "https://stackoverflow.com/questions/6890565/how-do-i-decompose-a-url-into-its-component-parts-in-java";
        stackOverflowParseResult = (StackOverflowParseResult) h1.handle(link);

        URL url = new URL(link);
        System.out.println(url.getPath());

        String result = "";
        Pattern regex = Pattern.compile("\\d+(?<=[^d])(?=/)");
        Matcher regexMatcher = regex.matcher(link);
        if (regexMatcher.find()) {
            result = regexMatcher.group();
        }

        System.out.println(result);

    }

    public static boolean isValidURL(String url) {
        UrlValidator validator = new UrlValidator();
        return validator.isValid(url);
    }

    public static boolean check(Predicate<String> predicate, String str) {
        return predicate.test(str);
    }
}
