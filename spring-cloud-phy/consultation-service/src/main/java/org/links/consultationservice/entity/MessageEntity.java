package org.links.consultationservice.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 消息实体类，表示会话中的一条对话消息记录。
 * <p>每条消息关联一个会话，记录角色、内容及时间戳。</p>
 */
@Data
@Entity
@Table(name = "message")
public class MessageEntity {

    /**
     * 消息主键 ID，自动生成。
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 所属会话对象，多对一关联。
     * <p>{@code @JsonBackReference} 与 {@code Conversation.messages} 的 {@code @JsonManagedReference} 配合使用，防止序列化死循环。</p>
     */
    @ManyToOne
    @JoinColumn(name = "conversation_id", nullable = false)
    @JsonBackReference
    private Conversation conversation;

    /**
     * 消息角色，如 "user" 或 "assistant"，用于区分发言方。
     */
    private String role;

    /**
     * 消息内容，使用长文本存储。
     */
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String content;

    /**
     * 消息的时间戳，记录发送时间，默认为当前时间。
     */
    private LocalDateTime timestamp = LocalDateTime.now();

    /**
     * 全参构造方法。
     *
     * @param conversation 会话对象
     * @param role         消息角色
     * @param content      消息内容
     */
    public MessageEntity(Conversation conversation, String role, String content) {
        this.conversation = conversation;
        this.role = role;
        this.content = content;
        this.timestamp = LocalDateTime.now();
    }

    /**
     * 无参构造方法，供 JPA 使用。
     */
    public MessageEntity() {
    }
}
