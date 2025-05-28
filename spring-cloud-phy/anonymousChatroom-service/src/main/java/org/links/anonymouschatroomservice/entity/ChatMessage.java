package org.links.anonymouschatroomservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "chat_message")
@Data
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageId;

    @Column(name = "chat_id", nullable = false)
    private String chatId;

    @Column(name = "from_user", nullable = false)
    private Long fromUser;

    @Column(name = "to_user", nullable = false)
    private Long toUser;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "send_time")
    private LocalDateTime sendTime;

    @Column(name = "is_read")
    private Boolean isRead = false;
}
