package ru.tinkoff.edu.java.scrapper.dao.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
@Entity
@Table(name = "chat")
public class TgChat {
    @NonNull
    @Min(0)
    @Id
    @Column(name = "chat_id")
    private Long chatId;

    public TgChat() {

    }

    @Getter
    @ManyToMany(mappedBy = "tgChats", fetch = FetchType.LAZY)
    private List<Link> links;
}
