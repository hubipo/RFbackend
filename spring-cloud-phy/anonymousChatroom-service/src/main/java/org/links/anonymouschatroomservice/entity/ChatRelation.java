package org.links.anonymouschatroomservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "chat_relation")
@Data
public class ChatRelation {

    @Id
    @Column(name = "chat_id", nullable = false)
    private Long chatId;  // 推荐用 UUID

    @Column(name = "user_1_id", nullable = false)
    private Long user1Id;

    @Column(name = "user_2_id", nullable = false)
    private Long user2Id;

    @Column(name = "tag", nullable = false)
    private String tag;

    @Column(name = "created_time")
    private LocalDateTime createdTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ChatRelationStatus status;

    public enum ChatRelationStatus {
        ACTIVE,
        DELETED,
        BLOCKED
    }
}
