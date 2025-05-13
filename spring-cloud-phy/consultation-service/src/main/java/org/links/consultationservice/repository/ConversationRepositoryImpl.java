package org.links.consultationservice.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.links.consultationservice.dto.ConversationSearchResult;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ConversationRepositoryImpl implements ConversationRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<ConversationSearchResult> searchByKeywords(List<String> keywords, Long userId) {
        StringBuilder sb = new StringBuilder();
        sb.append("""
            SELECT DISTINCT c.id AS id,
                   c.title AS title,
                   m.content AS matchSnippet,
                   c.created_at AS createdAt
            FROM conversation c
            LEFT JOIN message m ON c.id = m.conversation_id
            WHERE c.user_id = :userId AND (
        """);

        for (int i = 0; i < keywords.size(); i++) {
            String param = ":kw" + i;
            if (i > 0) sb.append(" OR ");
            sb.append("LOWER(c.title) LIKE LOWER(CONCAT('%', ").append(param).append(", '%'))");
            sb.append(" OR LOWER(m.content) LIKE LOWER(CONCAT('%', ").append(param).append(", '%'))");
        }

        sb.append(") ORDER BY c.created_at DESC");

        Query query = entityManager.createNativeQuery(sb.toString(), "ConversationSearchResultMapping");
        query.setParameter("userId", userId);
        for (int i = 0; i < keywords.size(); i++) {
            query.setParameter("kw" + i, keywords.get(i));
        }

        return query.getResultList();
    }
}
