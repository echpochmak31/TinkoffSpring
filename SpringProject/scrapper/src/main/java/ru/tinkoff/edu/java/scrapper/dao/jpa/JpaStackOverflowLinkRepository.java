package ru.tinkoff.edu.java.scrapper.dao.jpa;

import jakarta.validation.constraints.Min;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.tinkoff.edu.java.scrapper.dao.models.StackOverflowLink;

public interface JpaStackOverflowLinkRepository extends JpaRepository<StackOverflowLink, Long> {
    @Modifying
    @Query("DELETE FROM StackOverflowLink sol WHERE sol.linkId = :link_id")
    void safeDeleteById(@Param("link_id") @Min(0) long linkId);

}
