package ru.tinkoff.edu.java.parser;


import org.apache.commons.validator.routines.UrlValidator;
import ru.tinkoff.edu.java.parser.handlers.DefaultUrlChecker;
import ru.tinkoff.edu.java.parser.handlers.Handler;
import ru.tinkoff.edu.java.parser.handlers.LinkHandler;
import ru.tinkoff.edu.java.parser.handlers.LinkValidator;
import ru.tinkoff.edu.java.parser.results.StackOverflowParseResult;

import java.util.function.Predicate;

public class Main {
    public static void main(String[] args) {
        StackOverflowParseResult stackOverflowParseResult;
        try {
            Handler h1 = new LinkValidator(new DefaultUrlChecker(new UrlValidator()));
            Handler h2 = new LinkHandler<>(Main::isValidURL, StackOverflowParseResult.class);
            h1.setNext(h2);

            var result = h1.handle("http://baeldung.com/");
            stackOverflowParseResult = (StackOverflowParseResult) result;
            System.out.println(result.getUrl());
            stackOverflowParseResult.getQuestionId();

        }
        catch (Exception e) {
            System.out.println(e);
        }

    }

    public static boolean isValidURL(String url) {
        UrlValidator validator = new UrlValidator();
        return validator.isValid(url);
    }

    public static boolean check(Predicate<String> predicate, String str){
        return predicate.test(str);
    }
}



