package org.links.consultationservice.dto;

import org.links.consultationservice.entity.Conversation;
import java.time.LocalDateTime;

public class ConversationDto {
    private Long id;
    private Long userId;
    private String title;
    private LocalDateTime createdAt;

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
