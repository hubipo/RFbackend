package org.links.consultationservice.entity;

import jakarta.persistence.*;

/**
 * 会话搜索结果映射实体类，仅用于支持 {@code @SqlResultSetMapping} 映射。
 *
 * <p>由于 JPA 要求映射结果类必须与某个实体类关联，因此创建该“空壳”实体用于承载
 * {@link SqlResultSetMapping} 配置。</p>
 *
 * <p>注意：本类不会参与业务逻辑，也不会实际插入数据库，仅作为查询映射载体。</p>
 */
@SqlResultSetMapping(
        name = "ConversationSearchResultMapping",
        classes = @ConstructorResult(
                targetClass = org.links.consultationservice.dto.ConversationSearchResultImpl.class,
                columns = {
                        @ColumnResult(name = "id", type = Long.class),             // 会话 ID
                        @ColumnResult(name = "title", type = String.class),        // 会话标题
                        @ColumnResult(name = "matchSnippet", type = String.class), // 匹配内容片段
                        @ColumnResult(name = "createdAt", type = String.class)     // 创建时间字符串
                }
        )
)
@Entity
public class ConversationSearchResultEntity {

    /**
     * 虚拟主键字段，仅为满足 @Entity 要求。
     */
    @Id
    private Long id;
}
