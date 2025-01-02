package org.links.driftingbottleservice.dto;

/**
 * DTO for responding with details of a comment on a drifting bottle.
 */
public class BottleCommentResponse {
    private Long userId; // 评论者的用户ID
    private String content; // 评论的内容
    private String createdAt; // 评论的创建时间

    // Constructor
    public BottleCommentResponse(Long userId, String content, String createdAt) {
        this.userId = userId;
        this.content = content;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
