package org.links.driftingbottleservice.dto;

/**
 * DTO for creating a new drifting bottle.
 */
public class BottleRequest {
    private String phoneNumber; // 用户的手机号，用于通过用户系统获取用户ID
    private String content; // 漂流瓶的内容

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
