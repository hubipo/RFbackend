package org.links.driftingbottleservice.dto;

/**
 * 漂流瓶评论响应 DTO。
 * <p>用于向客户端返回某条评论的详细信息。</p>
 */
public class BottleCommentResponse {

    /**
     * 评论者的用户 ID。
     */
    private Long userId;

    /**
     * 评论内容。
     */
    private String content;

    /**
     * 评论创建时间，格式为字符串。
     */
    private String createdAt;

    /**
     * 构造函数。
     *
     * @param userId 评论者的用户 ID
     * @param content 评论内容
     * @param createdAt 评论创建时间
     */
    public BottleCommentResponse(Long userId, String content, String createdAt) {
        this.userId = userId;
        this.content = content;
        this.createdAt = createdAt;
    }

    /**
     * 获取评论者用户 ID。
     *
     * @return 用户 ID
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置评论者用户 ID。
     *
     * @param userId 用户 ID
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取评论内容。
     *
     * @return 评论文本
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置评论内容。
     *
     * @param content 评论文本
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获取评论创建时间。
     *
     * @return 创建时间字符串
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * 设置评论创建时间。
     *
     * @param createdAt 创建时间字符串
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
