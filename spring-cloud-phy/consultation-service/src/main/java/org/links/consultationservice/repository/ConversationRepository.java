package org.links.consultationservice.repository;

import feign.Param;
import org.links.consultationservice.entity.Conversation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    List<Conversation> findByUserId(Long userId);

    Page<Conversation> findByUserId(Long userId, Pageable pageable);

    @Query("""
        SELECT DISTINCT c FROM Conversation c
        LEFT JOIN c.messages m
        WHERE c.userId = :userId AND (
            LOWER(c.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
            LOWER(m.content) LIKE LOWER(CONCAT('%', :keyword, '%'))
        )
    """)
    Page<Conversation> searchByTitleOrMessage(@Param("userId") Long userId, @Param("keyword") String keyword, Pageable pageable);
}
