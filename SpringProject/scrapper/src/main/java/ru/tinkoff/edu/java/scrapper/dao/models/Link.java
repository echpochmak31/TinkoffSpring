package ru.tinkoff.edu.java.scrapper.dao.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.hibernate.validator.constraints.URL;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@ToString
@AllArgsConstructor
@Builder
@Entity
@Table(name = "link", schema = "links")
@SequenceGenerator(name = "db_sequence", schema = "links", sequenceName = "link_link_id_seq", allocationSize = 20)
public class Link {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "db_sequence")
    @Column(name = "link_id")
    private Long linkId;

    @NonNull
    @NotBlank
    @URL
    @Column(name = "url")
    private String url;

    @PastOrPresent
    @Column(name = "last_update")
    private OffsetDateTime lastUpdate;

    @PastOrPresent
    @Column(name = "last_check")
    private OffsetDateTime lastCheck;
    @Getter
    @Builder.Default
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "link_chat",
        schema = "links",
        joinColumns = @JoinColumn(name = "link_id"),
        inverseJoinColumns = @JoinColumn(name = "chat_id")
    )
    private List<TgChat> tgChats = new ArrayList<>();

    public Link() {

    }

    public void addChatIfNotExists(@NonNull TgChat tgChat) {
        if (!tgChats.contains(tgChat)) {
            tgChats.add(tgChat);
        }
    }

    public boolean removeChat(@NonNull TgChat tgChat) {
        return tgChats.remove(tgChat);
    }
}
