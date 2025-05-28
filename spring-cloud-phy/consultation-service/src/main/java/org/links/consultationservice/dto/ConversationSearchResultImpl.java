package org.links.consultationservice.dto;

import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.SqlResultSetMapping;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 使用原生 SQL 查询返回的会话搜索结果映射类。
 * <p>该类实现了 {@link ConversationSearchResult} 接口，用于封装多关键词搜索返回的字段。</p>
 *
 * <p>{@code @SqlResultSetMapping} 定义了 SQL 查询结果如何映射到该类的构造函数参数上。</p>
 */
@SqlResultSetMapping(
        name = "ConversationSearchResultMapping", // 映射名称，可在查询中引用
        classes = @ConstructorResult(
                targetClass = org.links.consultationservice.dto.ConversationSearchResultImpl.class,
                columns = {
                        @ColumnResult(name = "id", type = Long.class),             // 会话 ID
                        @ColumnResult(name = "title", type = String.class),        // 会话标题
                        @ColumnResult(name = "matchSnippet", type = String.class), // 匹配内容摘要
                        @ColumnResult(name = "createdAt", type = String.class)     // 创建时间（字符串）
                }
        )
)
@Data
@AllArgsConstructor
public class ConversationSearchResultImpl implements ConversationSearchResult {

    /**
     * 会话的唯一标识符。
     */
    private Long id;

    /**
     * 会话标题。
     */
    private String title;

    /**
     * 匹配的内容摘要，便于前端高亮展示。
     */
    private String matchSnippet;

    /**
     * 会话创建时间（已格式化字符串）。
     */
    private String createdAt;
}
