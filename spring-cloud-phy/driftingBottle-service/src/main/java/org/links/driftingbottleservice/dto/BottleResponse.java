package org.links.driftingbottleservice.dto;

/**
 * DTO for responding with drifting bottle details.
 */
public class BottleResponse {
    private Long id; // 漂流瓶的ID
    private Long ownerId; // 漂流瓶的拥有者ID
    private String content; // 漂流瓶的内容
    private String createdAt; // 漂流瓶的创建时间

    // Constructor
    public BottleResponse(Long id, Long ownerId, String content, String createdAt) {
        this.id = id;
        this.ownerId = ownerId;
        this.content = content;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
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
