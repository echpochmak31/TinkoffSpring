package ru.tinkoff.edu.java.scrapper.dao.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;
import org.hibernate.validator.constraints.URL;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@ToString
@AllArgsConstructor
@Builder
@Entity
@Table(name = "link")
public class Link {
    @NonNull
    @Min(0)
    @Id
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

    public Link() {

    }

    @Getter
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "link_chat",
            joinColumns = @JoinColumn(name = "link_id"),
            inverseJoinColumns = @JoinColumn(name = "chat_id")
    )
    private List<TgChat> tgChats;

}
