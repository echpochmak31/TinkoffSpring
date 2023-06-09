package ru.tinkoff.edu.java.scrapper.dao.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
@Entity
@Table(name = "chat", schema = "links")
public class TgChat {
    @NonNull
    @Min(0)
    @Id
    @Column(name = "chat_id")
    private Long chatId;

    public TgChat() {

    }

    @Getter
    @Builder.Default
    @ManyToMany(mappedBy = "tgChats", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Link> links = new ArrayList<>();

    public void removeLink(Link link) {
        links.remove(link);
    }
}
