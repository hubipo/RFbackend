package org.links.consultationservice.dto;

import org.links.consultationservice.entity.Conversation;

import java.time.LocalDateTime;

/**
 * 会话信息的数据传输对象（DTO）。
 * <p>用于接口层向客户端返回精简后的会话信息。</p>
 *
 * <ul>
 *   <li>包含会话 ID、用户 ID、标题、创建时间等基础信息</li>
 *   <li>通过 {@link Conversation} 实体对象构造</li>
 * </ul>
 */
public class ConversationDto {

    /**
     * 会话 ID
     */
    private Long id;

    /**
     * 所属用户的 ID
     */
    private Long userId;

    /**
     * 会话标题
     */
    private String title;

    /**
     * 会话创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 根据会话实体构造 DTO
     *
     * @param conversation 会话实体对象
     */
    public ConversationDto(Conversation conversation) {
        this.id = conversation.getId();
        this.userId = conversation.getUserId();
        this.title = conversation.getTitle();
        this.createdAt = conversation.getCreatedAt();
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
