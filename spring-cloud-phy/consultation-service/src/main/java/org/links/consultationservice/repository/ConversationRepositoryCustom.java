package org.links.consultationservice.repository;

import org.links.consultationservice.dto.ConversationSearchResult;

import java.util.List;

/**
 * 自定义会话搜索仓库接口。
 * <p>用于扩展对 {@code Conversation} 实体的复杂查询逻辑，主要用于多关键词搜索。</p>
 *
 * <p>该接口需由一个实现类（如 {@code ConversationRepositoryImpl}）实现，配合 {@code EntityManager} 编写原生 SQL 查询。</p>
 */
public interface ConversationRepositoryCustom {

    /**
     * 根据多个关键词和用户 ID 进行全文搜索，返回匹配的会话摘要结果。
     *
     * @param keywords 关键词列表，多个词之间为“与”关系（AND）
     * @param userId   用户 ID，仅搜索该用户的会话内容
     * @return 匹配的搜索结果列表，包含会话标题、摘要片段等
     */
    List<ConversationSearchResult> searchByKeywords(List<String> keywords, Long userId);
}
