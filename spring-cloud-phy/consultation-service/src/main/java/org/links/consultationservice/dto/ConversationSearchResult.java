package org.links.consultationservice.dto;

/**
 * 会话搜索结果接口。
 * <p>用于定义多关键词搜索接口的结果结构，通常与 JPQL 或原生 SQL 查询结果映射使用。</p>
 *
 * <p>该接口常用于返回包含匹配片段的会话列表，例如在关键词搜索功能中展示命中摘要。</p>
 */
public interface ConversationSearchResult {

    /**
     * 获取会话 ID。
     *
     * @return 会话的唯一标识符
     */
    Long getId();

    /**
     * 获取会话标题。
     *
     * @return 会话标题字符串
     */
    String getTitle();

    /**
     * 获取与关键词匹配的片段（摘要）。
     *
     * @return 匹配的片段文本（通常带高亮）
     */
    String getMatchSnippet();

    /**
     * 获取会话创建时间（字符串格式）。
     *
     * @return 会话的创建时间，字符串形式
     */
    String getCreatedAt();
}
