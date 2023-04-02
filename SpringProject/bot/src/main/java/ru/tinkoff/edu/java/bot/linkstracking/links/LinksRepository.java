package ru.tinkoff.edu.java.bot.linkstracking.links;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.hibernate.validator.constraints.URL;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Supplier;

@Component
@AllArgsConstructor
public class LinksRepository implements Supplier<List<String>>, LinksTracker, LinksUntracker {
    private List<String> links;

    @Override
    public List<String> get() {
        return links;
    }

    @Override
    public void track(@NonNull @URL String link) {
        links.add(link);
    }

    @Override
    public boolean untrack(@NonNull @URL String link) {
        return links.remove(link);
    }
}
