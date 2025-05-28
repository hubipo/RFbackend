package org.links.consultationservice.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.links.consultationservice.dto.ConversationSearchResult;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * {@link ConversationRepositoryCustom} 的实现类。
 * <p>提供基于多个关键词的全文搜索功能，支持用户在其所有会话中查找匹配的内容片段。</p>
 */
@Repository
public class ConversationRepositoryImpl implements ConversationRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 执行基于原生 SQL 的多关键词搜索，会返回包含匹配片段的结果。
     *
     * @param keywords 关键词列表（默认按 OR 逻辑组合）
     * @param userId   用户 ID，只查询该用户的对话
     * @return 包含匹配摘要片段的搜索结果列表
     */
    @Override
    public List<ConversationSearchResult> searchByKeywords(List<String> keywords, Long userId) {
        // 构建 SQL 查询语句
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

        // 拼接关键词匹配条件（title 或 message 内容中包含任一关键词）
        for (int i = 0; i < keywords.size(); i++) {
            String param = ":kw" + i;
            if (i > 0) sb.append(" OR ");
            sb.append("LOWER(c.title) LIKE LOWER(CONCAT('%', ").append(param).append(", '%'))");
            sb.append(" OR LOWER(m.content) LIKE LOWER(CONCAT('%', ").append(param).append(", '%'))");
        }

        sb.append(") ORDER BY c.created_at DESC");

        // 创建原生查询并设置参数
        Query query = entityManager.createNativeQuery(sb.toString(), "ConversationSearchResultMapping");
        query.setParameter("userId", userId);
        for (int i = 0; i < keywords.size(); i++) {
            query.setParameter("kw" + i, keywords.get(i));
        }

        // 返回查询结果（自动映射到 ConversationSearchResult 接口）
        return query.getResultList();
    }
}
