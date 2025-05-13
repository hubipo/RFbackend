package org.links.consultationservice.entity;

import jakarta.persistence.*;

@SqlResultSetMapping(
        name = "ConversationSearchResultMapping",
        classes = @ConstructorResult(
                targetClass = org.links.consultationservice.dto.ConversationSearchResultImpl.class,
                columns = {
                        @ColumnResult(name = "id", type = Long.class),
                        @ColumnResult(name = "title", type = String.class),
                        @ColumnResult(name = "matchSnippet", type = String.class),
                        @ColumnResult(name = "createdAt", type = String.class)
                }
        )
)
@Entity
public class ConversationSearchResultEntity {
    @Id
    private Long id; // dummy 字段，仅用于让 @Entity 成立
}
