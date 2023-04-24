package ru.tinkoff.edu.java.scrapper.dao.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tinkoff.edu.java.scrapper.dao.models.StackOverflowLink;

public interface JpaStackOverflowLinkRepository extends JpaRepository<StackOverflowLink, Long> {
}
