package ru.tinkoff.edu.java.scrapper.dao.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@AllArgsConstructor
@Builder
@Entity
@Table(name = "stackoverflow_link", schema = "links")
public class StackOverflowLink {
    @NonNull
    @Min(0)
    @Id
    @Column(name = "link_id")
    private Long linkId;

    @NonNull
    @Min(0)
    @Column(name = "comment_amount")
    private Integer commentAmount;

    @NonNull
    @Min(0)
    @Column(name = "answers_amount")
    private Integer answersAmount;

    @NonNull
    @Column(name = "is_answered")
    private Boolean isAnswered;

    public StackOverflowLink() {

    }

    @OneToOne
    public Link link;
}
