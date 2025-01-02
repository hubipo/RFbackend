package org.links.driftingbottleservice.dto;

/**
 * DTO for creating a comment on a drifting bottle.
 */
public class BottleCommentRequest {
    private String phoneNumber; // 评论者的手机号
    private String content; // 评论的内容

    // Getters and Setters
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
