package org.links.consultationservice.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 会话实体类，表示一次用户与系统的咨询会话。
 * <p>包含会话的标题、所属用户、创建时间，以及该会话下的所有消息记录。</p>
 */
@Data
@Entity
@Table(name = "conversation")
public class Conversation {

    /**
     * 主键，自增生成的会话 ID。
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 发起会话的用户 ID。
     */
    private Long userId;

    /**
     * 会话标题。可由用户自定义，用于区分多个会话。
     */
    private String title;

    /**
     * 会话创建时间，默认为当前时间。
     */
    private LocalDateTime createdAt = LocalDateTime.now();

    /**
     * 与该会话关联的所有消息列表。
     * <p>使用 {@code cascade = CascadeType.ALL} 实现级联操作；{@code orphanRemoval = true} 表示删除会话时删除其所有消息。</p>
     * <p>{@code @JsonManagedReference} 用于处理与 {@code MessageEntity} 的双向序列化关系，防止循环引用。</p>
     */
    @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<MessageEntity> messages = new ArrayList<>();
}
