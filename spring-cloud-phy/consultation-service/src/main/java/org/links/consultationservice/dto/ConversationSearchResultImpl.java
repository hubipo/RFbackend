package org.links.consultationservice.dto;

import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.SqlResultSetMapping;
import lombok.AllArgsConstructor;
import lombok.Data;

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

@Data
@AllArgsConstructor
public class ConversationSearchResultImpl implements ConversationSearchResult {
    private Long id;
    private String title;
    private String matchSnippet;
    private String createdAt;
}
